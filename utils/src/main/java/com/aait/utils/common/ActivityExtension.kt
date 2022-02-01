package com.aait.utils.common

import android.app.Activity
import android.content.Intent
import android.net.Uri
import java.util.*

fun Activity.openMap(lat: Double, lon: Double) {
    val uri = java.lang.String.format(
        Locale.ENGLISH,
        "http://maps.google.com/maps?daddr=%f,%f (%s)",
        lat,
        lon,
        "cairo"
    )
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    startActivity(intent)
}