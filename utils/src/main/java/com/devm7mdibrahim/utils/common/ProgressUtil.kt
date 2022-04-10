package com.devm7mdibrahim.utils.common

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.devm7mdibrahim.utils.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressUtil @Inject constructor(val context: Context) {


    private var dialog: AlertDialog? = null

    init {
        init()
    }

    @SuppressLint("InflateParams")
    private fun init() {
        dialog = AlertDialog.Builder(context).create()
        dialog?.apply {
            val inflate = LayoutInflater.from(context).inflate(R.layout.progress, null)
            setView(inflate)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
        }

        dialog?.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    fun showProgress() {
        if (dialog != null && dialog?.isShowing == false) {
            dialog?.show()
        }
    }


    fun hideProgress() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }
}
