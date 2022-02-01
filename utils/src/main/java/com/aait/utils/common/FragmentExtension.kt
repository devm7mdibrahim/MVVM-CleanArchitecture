package com.aait.utils.common

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.setFragmentResult(requestKey: String, result: Bundle) {
    parentFragmentManager.setFragmentResult(requestKey, result)
}

fun Fragment.setFragmentResultListener(
    requestKey: String,
    listener: ((requestKey: String, bundle: Bundle) -> Unit)
) {
    parentFragmentManager.setFragmentResultListener(requestKey, this, listener)
}