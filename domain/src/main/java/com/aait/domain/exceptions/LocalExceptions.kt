package com.aait.domain.exceptions

sealed class LocalExceptions : Exception() {
    object UnknownException : LocalExceptions()
    object TimeoutException : LocalExceptions()
}