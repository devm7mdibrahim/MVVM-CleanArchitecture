package com.aait.data.remote.utils

import com.aait.data.remote.utils.NetworkConstants.NETWORK_TIMEOUT
import com.aait.domain.entities.BaseResponse
import com.aait.domain.exceptions.NetworkExceptions
import com.aait.domain.util.Constants
import com.aait.domain.util.Constants.FAIL
import com.aait.domain.util.Constants.SUCCESS
import com.aait.domain.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Flow<DataState<T>> = flow {
    withTimeout(NETWORK_TIMEOUT) {
        val response = apiCall.invoke()
        emit(handleSuccess(response))
    }
}.onStart {
    emit(DataState.Loading)
}.catch {
    emit(handleError(it))
}.flowOn(Dispatchers.IO)

fun <T> handleSuccess(response: T): DataState<T> {
    if (response != null) {
        val baseResponse = response as BaseResponse<*>
        if (baseResponse.key == SUCCESS) {
            if (baseResponse.userStatus == Constants.ACTIVE
                || baseResponse.userStatus == Constants.PENDING
                || baseResponse.userStatus.isEmpty()
            ) {
                return if (baseResponse.code == Constants.SUCCESS_CODE) {
                    DataState.Success(response)
                } else {
                    DataState.Error(NetworkExceptions.CustomException(baseResponse.msg))
                }
            } else if (baseResponse.userStatus == Constants.BLOCK) {
                return DataState.Error(NetworkExceptions.AuthorizationException)
            }
        } else if (baseResponse.key == FAIL) {
            return if (baseResponse.code == Constants.UNAUTHORIZED_CODE) {
                DataState.Error(NetworkExceptions.AuthorizationException)
            } else {
                DataState.Error(NetworkExceptions.CustomException(baseResponse.msg))
            }
        }
    }

    return DataState.Error(NetworkExceptions.UnknownException)
}

fun <T> handleError(it: Throwable): DataState<T> {
    it.printStackTrace()
    return when (it) {
        is TimeoutCancellationException -> {
            DataState.Error(NetworkExceptions.TimeoutException)
        }

        is UnknownHostException -> {
            DataState.Error(NetworkExceptions.ConnectionException)
        }

        is IOException -> {
            DataState.Error(NetworkExceptions.UnknownException)
        }

        is HttpException -> {
            DataState.Error(convertErrorBody(it))
        }

        else -> {
            DataState.Error(NetworkExceptions.UnknownException)
        }
    }
}

private fun convertErrorBody(throwable: HttpException): Exception {
    return when (throwable.code()) {
        401, 419 -> NetworkExceptions.AuthorizationException
        404 -> NetworkExceptions.NotFoundException
        500 -> NetworkExceptions.ServerException
        else -> NetworkExceptions.UnknownException
    }
}