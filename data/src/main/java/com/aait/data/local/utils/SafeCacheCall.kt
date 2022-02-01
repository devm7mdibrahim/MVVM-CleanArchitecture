package com.aait.data.local.utils

import com.aait.domain.exceptions.LocalExceptions
import com.aait.domain.util.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout

const val CACHE_TIMEOUT = 30000L

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): Flow<DataState<T>> = flow {
    withTimeout(CACHE_TIMEOUT) {
        val response = cacheCall.invoke()

        if (response != null) {
            emit(DataState.Success(response))
        } else {
            emit(DataState.Error(LocalExceptions.UnknownException))
        }
    }
}.onStart {
    emit(DataState.Loading)
}.catch {
    when (it) {
        is TimeoutCancellationException -> {
            emit(DataState.Error(LocalExceptions.TimeoutException))
        }
        else -> {
            emit(DataState.Error(LocalExceptions.UnknownException))
        }
    }
}.flowOn(dispatcher)