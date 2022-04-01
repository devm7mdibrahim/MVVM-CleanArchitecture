package com.devm7mdibrahim.data.remote.utils

import com.devm7mdibrahim.data.remote.utils.NetworkConstants.FailRequestCode.BLOCKED
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.FailRequestCode.EXCEPTION
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.FailRequestCode.FAIL
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.FailRequestCode.UN_AUTH
import com.devm7mdibrahim.data.remote.utils.NetworkConstants.NETWORK_TIMEOUT
import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.exceptions.NetworkExceptions
import com.devm7mdibrahim.domain.util.DataState
import com.google.gson.Gson
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
    return if (response != null) {
        DataState.Success(response)
    } else {
        DataState.Error(NetworkExceptions.UnknownException)
    }
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
    val errorBody = throwable.response()?.errorBody()?.charStream()
    val response = Gson().fromJson(errorBody, BaseResponse::class.java)
    return when (throwable.code()) {
        FAIL -> NetworkExceptions.CustomException(response.msg)
        UN_AUTH, BLOCKED -> NetworkExceptions.AuthorizationException
        EXCEPTION -> NetworkExceptions.ServerException
        else -> NetworkExceptions.UnknownException
    }
}