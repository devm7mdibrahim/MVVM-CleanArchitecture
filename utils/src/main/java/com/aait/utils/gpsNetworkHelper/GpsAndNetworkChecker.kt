package com.aait.utils.gpsNetworkHelper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager

class GpsAndNetworkChecker : BroadcastReceiver() {

    var gpsNetworkHelper: GpsNetworkHelper? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action!!.matches(Regex("android.location.PROVIDERS_CHANGED"))) {
            // react on GPS provider change action
            gpsStatusTracker(context)
        } else if (intent.action!!.matches(Regex("android.net.conn.CONNECTIVITY_CHANGE"))) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                if (gpsNetworkHelper != null) {
                    gpsNetworkHelper?.isNetworkEnabled(true)
                }
            } else {
                // not connected to the internet
                if (gpsNetworkHelper != null) {
                    gpsNetworkHelper?.isNetworkEnabled(false)
                }
            }
        }
    }

    private fun gpsStatusTracker(mContext: Context) {

        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (isNetworkEnabled) {
            //location is enabled
            if (gpsNetworkHelper != null) {
                gpsNetworkHelper?.isGpsEnabled(true)
            }
        } else {
            //location is disabled
            if (gpsNetworkHelper != null) {
                gpsNetworkHelper?.isGpsEnabled(false)
            }
        }
        if (isGpsEnabled) {
            //location is enabled
            if (gpsNetworkHelper != null) {
                gpsNetworkHelper?.isGpsEnabled(true)
            }
        } else {
            //location is disabled
            if (gpsNetworkHelper != null) {
                gpsNetworkHelper?.isGpsEnabled(false)
            }
        }
    }

}
