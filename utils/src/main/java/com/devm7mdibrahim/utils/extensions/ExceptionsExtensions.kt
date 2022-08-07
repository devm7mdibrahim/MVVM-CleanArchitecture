package com.devm7mdibrahim.utils.extensions

import com.google.firebase.crashlytics.FirebaseCrashlytics

fun Throwable.log() {
    printStackTrace()
    FirebaseCrashlytics.getInstance().recordException(this)
}

fun catch(action: () -> Unit) =
    try {
        action.invoke()
    } catch (throwable: Throwable) {
        throwable.log()
    }