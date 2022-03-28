package com.devm7mdibrahim.presentation.utils

import com.devm7mdibrahim.domain.exceptions.NetworkExceptions
import com.devm7mdibrahim.presentation.R


fun Throwable.getIsCommonException(): Int {
    when (this) {
        is NetworkExceptions.ConnectionException -> {
            return R.string.error_connection
        }

        is NetworkExceptions.NotFoundException -> {
            return R.string.error_not_found
        }

        is NetworkExceptions.ServerException -> {
            return R.string.error_server
        }

        is NetworkExceptions.TimeoutException -> {
            return R.string.error_timeout
        }

        is NetworkExceptions.UnknownException -> {
            return R.string.error_unknown
        }

        else -> {
            return R.string.error_unknown
        }
    }
}