package com.aait.utils.common

import android.content.Context
import android.widget.Toast
import com.aait.utils.R
import es.dmoral.toasty.Toasty

fun Context.showToast(message: String?, toastType: ToastType = ToastType.ERROR, withIcon: Boolean = true) {
    if (message.isNullOrEmpty()) return
    when (toastType) {
        ToastType.SUCCESS -> {
            Toasty.success(this, message, Toast.LENGTH_SHORT, withIcon).show()
        }
        ToastType.ERROR -> {
            Toasty.error(this, message, Toast.LENGTH_SHORT, withIcon).show()
        }
        ToastType.INFO -> {
            Toasty.info(this, message, Toast.LENGTH_SHORT, withIcon).show()
        }
        ToastType.WARNING -> {
            Toasty.warning(this, message, Toast.LENGTH_SHORT, withIcon).show()
        }
    }
}

fun Context.toastNoInternetConnection() {
    Toasty.error(
        this,
        getString(R.string.no_internet_connection),
        Toast.LENGTH_SHORT,
        true
    ).show()
}

enum class ToastType {
    SUCCESS, ERROR, WARNING, INFO
}
