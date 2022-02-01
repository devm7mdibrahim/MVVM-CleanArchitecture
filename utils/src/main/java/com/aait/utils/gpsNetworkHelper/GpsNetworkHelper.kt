package com.aait.utils.gpsNetworkHelper

import org.json.JSONObject

interface GpsNetworkHelper {

    fun isGpsEnabled(status: Boolean) {

    }

    fun isNetworkEnabled(status: Boolean) {

    }

    fun onChanelReceivedData(chanel: String, messageJson: JSONObject) {

    }

    fun closeSocketEvent() {

    }

}