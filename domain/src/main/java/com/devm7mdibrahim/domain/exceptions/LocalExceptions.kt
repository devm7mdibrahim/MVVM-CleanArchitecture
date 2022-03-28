package com.devm7mdibrahim.domain.exceptions

sealed class LocalExceptions : Exception() {
    object UnknownException : LocalExceptions()
    object TimeoutException : LocalExceptions()
}