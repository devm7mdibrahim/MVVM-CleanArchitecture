package com.devm7mdibrahim.data.remote.utils

import com.devm7mdibrahim.data.remote.utils.NetworkConstants.NETWORK_TIMEOUT
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.RequestKeys.BLOCKED
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.RequestKeys.EXCEPTION
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.RequestKeys.FAIL
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.RequestKeys.NEED_ACTIVE
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.RequestKeys.SUCCESS
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.RequestKeys.UN_AUTH
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.exceptions.NetworkExceptions
import com.devm7mdibrahim.domain.util.DataState
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
        return when (baseResponse.key) {
            SUCCESS -> {
                DataState.Success(response)
            }
            FAIL -> {
                DataState.Error(NetworkExceptions.CustomException(baseResponse.msg))
            }
            NEED_ACTIVE -> {
                DataState.Error(NetworkExceptions.NeedActiveException(baseResponse.msg))
            }
            UN_AUTH, BLOCKED -> {
                DataState.Error(NetworkExceptions.AuthorizationException)
            }
            EXCEPTION -> {
                DataState.Error(NetworkExceptions.CustomException(baseResponse.msg))
            }
            else ->
                DataState.Error(NetworkExceptions.UnknownException)
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