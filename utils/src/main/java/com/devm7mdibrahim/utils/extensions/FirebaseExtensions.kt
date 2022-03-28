package com.devm7mdibrahim.utils.extensions

import com.google.firebase.messaging.FirebaseMessaging

fun getFirebaseToken(token : (String) -> Unit) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener {
        if (it.isSuccessful) {
            token(it.result)
        } else {
            it.exception?.printStackTrace()
        }
    }
}