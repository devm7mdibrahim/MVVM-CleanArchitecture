package com.aait.utils.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import java.security.MessageDigest


object FacebookUtils {

    @Suppress("DEPRECATION")
    @SuppressLint("PackageManagerGetSignatures")
    fun Context.getHashKey() {
        kotlin.runCatching {
            val info = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )

            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                hashKey.onPrintLog("hash_key")
            }
        }
    }
}