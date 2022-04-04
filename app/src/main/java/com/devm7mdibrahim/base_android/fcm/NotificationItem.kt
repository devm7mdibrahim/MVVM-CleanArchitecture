package com.devm7mdibrahim.base_android.fcm

import com.google.gson.annotations.SerializedName

data class NotificationItem(
    @SerializedName("message_ar") val messageAr: String? = "",
    @SerializedName("message_en") val messageEn: String? = "",
    @SerializedName("title_ar") val titleAr: String? = "",
    @SerializedName("title_en") val titleEn: String? = "",
    @SerializedName("type") val type: String? = "",
)
