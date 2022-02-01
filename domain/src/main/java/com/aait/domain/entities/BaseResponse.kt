package com.aait.domain.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "key") val key: String,
    @Json(name = "value") val value: Int?,
    @Json(name = "data") val data: T?,
    @Json(name = "msg") val msg: String,
    @Json(name = "code") val code: Int,
    @Json(name = "user_status") val userStatus: String,
    @Json(name = "usermeta_status") val userMetaStatus: String?,
) : Serializable