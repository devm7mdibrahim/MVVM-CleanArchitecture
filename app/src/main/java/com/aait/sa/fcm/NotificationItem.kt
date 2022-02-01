package com.aait.sa.fcm

import com.google.gson.annotations.SerializedName

data class NotificationItem(
    @SerializedName("message_ar") val messageAr: String? = "",
    @SerializedName("message_en") val messageEn: String? = "",
    @SerializedName("title_ar") val titleAr: String? = "",
    @SerializedName("title_en") val titleEn: String? = "",
    @SerializedName("room_id") val roomId: Int?,
    @SerializedName("sender") val senderId: Int?,
    @SerializedName("sender_name") val senderName: String? = "",
    @SerializedName("sender_avatar") val senderAvatar: String? = "",
    @SerializedName("type") val type: String? = "",
    @SerializedName("order_type") val orderType: String? = "",
    @SerializedName("order_status") val orderStatus: String? = "",
    @SerializedName("order_id") val orderId: Int?,
)
