package com.aait.utils.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.*

fun Context.getAddressFromLatLng(lat: Double, lon: Double): String? {
    val addresses: List<Address>
    val geocoder = Geocoder(this, Locale.getDefault())

    addresses = geocoder.getFromLocation(
        lat,
        lon,
        1
    ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


    return addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
}