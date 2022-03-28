package com.devm7mdibrahim.domain.entities

import com.squareup.moshi.Json

data class MessagesItem(
    @Json(name = "sender_type") val senderType: String
)

