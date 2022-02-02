package com.aait.utils.common

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.aait.utils.R


fun Context.showAlertDialog(
    title: String? = null,
    msg: String? = null,
    drawable: Int? = null,
    listener: (Boolean) -> Unit
) {
    AlertDialog.Builder(this)
        .setCancelable(false)
        .setTitle(title ?: "")
        .setMessage(msg ?: "")
        .setPositiveButton(
            R.string.text_ok
        ) { _, _ ->
            listener(true)
        }
        .setNegativeButton(
            R.string.text_cancel
        ) { dialog, _ ->
            listener(false)
            dialog.cancel()
        }.setIcon(drawable ?: android.R.drawable.ic_dialog_alert)
        .show()
}

fun Context.openLoginDialog(listener: (Boolean) -> Unit) {
    showAlertDialog(
        getString(R.string.text_alert),
        getString(R.string.text_are_must_login_to_continue)
    ) {
        listener(it)
    }
}

fun Context.openAccountDeletedDialog(listener: (Boolean) -> Unit) {
    showAlertDialog(
        getString(R.string.text_alert),
        getString(R.string.block_massage)
    ) {
        listener(it)
    }
}

fun Context.openLogoutDialog(listener: (Boolean) -> Unit) {
    showAlertDialog(
        getString(R.string.text_alert),
        getString(R.string.text_are_you_sure_logout)
    ) {
        listener(it)
    }
}