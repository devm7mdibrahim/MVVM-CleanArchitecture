package com.devm7mdibrahim.utils.extensions

import android.content.Context
import android.util.Log
import com.devm7mdibrahim.utils.R
import com.google.gson.Gson


fun Any.onPrintLog(tag: String = "App LOG ==>>> ") {
    Log.d(tag, Gson().toJson(this))
}

fun Context.setPriceWithCurrency(
    price: String?,
    currency: String = getString(R.string.sar)
): String {
    if (price == null) return ""
    return buildString {
        append(price)
        append(" ")
        append(currency)
    }
}