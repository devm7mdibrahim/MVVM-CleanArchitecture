package com.aait.data.remote.utils

object NetworkConstants {
    const val LANGUAGE = "lang"
    const val BEARER = "Bearer "
    const val AUTHORIZATION = "Authorization"
    const val NETWORK_TIMEOUT = 60000L
    const val API_VERSION = "/api/"

    object Languages {
        const val ARABIC = "ar"
        const val ENGLISH = "en"
    }

    object NetworkParams {
        const val PHONE = "phone"
        const val PASSWORD = "password"
        const val DEVICE_ID = "device_id"
        const val DEVICE_TYPE = "device_type"
        const val TYPE_ANDROID = "android"
        const val EMAIL = "email"
        const val MSG = "message"
        const val NAME = "name"
        const val AVATAR = "avatar"
        const val CODE = "code"
        const val LAT = "lat"
        const val LNG = "long"
        const val ADDRESS = "address"
        const val PAGE = "page"
    }

    object EndPoints {
        const val LOGIN = "login"
        const val REGISTER = "register"
    }
}