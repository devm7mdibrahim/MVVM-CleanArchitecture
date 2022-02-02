package com.aait.domain.exceptions

sealed class NetworkExceptions : Exception() {
    object UnknownException : NetworkExceptions()
    object ServerException : NetworkExceptions()
    object NotFoundException : NetworkExceptions()
    object TimeoutException : NetworkExceptions()
    object ConnectionException : NetworkExceptions()
    object AuthorizationException : NetworkExceptions()
    data class CustomException(val msg: String) : NetworkExceptions()
    data class NeedActiveException(val msg: String) : NetworkExceptions()
}