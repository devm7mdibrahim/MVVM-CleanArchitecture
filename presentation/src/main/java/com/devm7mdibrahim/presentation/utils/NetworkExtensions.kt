package com.devm7mdibrahim.presentation.utils

import com.devm7mdibrahim.domain.entities.BaseResponse
import com.devm7mdibrahim.domain.exceptions.NetworkExceptions
import com.devm7mdibrahim.domain.util.DataState

interface NetworkExtensionsActions {
    fun onLoad(showLoading: Boolean) {

    }

    fun onCommonError(exceptionMsgId: Int) {

    }

    fun onShowSuccessToast(msg: String?) {

    }

    fun onFail(msg: String?) {

    }

    fun authorizationFail() {

    }

    fun waitingApproval(msg: String?) {

    }

    fun authorizationNeedActive(msg: String) {

    }
}

fun <T> DataState<T>.applyCommonSideEffects(
    networkExtensionsActions: NetworkExtensionsActions,
    showLoading: Boolean = true,
    showSuccessToast: Boolean = true,
    onSuccess: (T) -> Unit = {}
) {
    when (this) {
        is DataState.Loading -> {
            if (showLoading) networkExtensionsActions.onLoad(true)
        }

        is DataState.Success -> {
            networkExtensionsActions.onLoad(false)
            if (showSuccessToast) networkExtensionsActions.onShowSuccessToast((data as BaseResponse<*>).msg)
            onSuccess(this.data)
        }

        is DataState.Error -> {
            networkExtensionsActions.onLoad(false)
            handleError(networkExtensionsActions, throwable)
        }

        DataState.Idle -> {
            networkExtensionsActions.onLoad(false)
        }
    }
}

private fun handleError(
    networkExtensionsActions: NetworkExtensionsActions,
    throwable: Throwable,
) {
    when (throwable) {
        is NetworkExceptions.AuthorizationException -> {
            networkExtensionsActions.authorizationFail()
        }

        is NetworkExceptions.NeedActiveException -> {
            networkExtensionsActions.authorizationNeedActive(throwable.msg)
        }

        is NetworkExceptions.CustomException -> {
            networkExtensionsActions.onFail(throwable.msg)
        }

        else -> {
            networkExtensionsActions.onCommonError(throwable.getIsCommonException())
        }
    }

}
