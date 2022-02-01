package com.aait.domain.entities

import com.squareup.moshi.Json

data class Pagination(
    @Json(name = "per_page") val perPage: Int = 0,
    @Json(name = "total") val total: Int = 0,
    @Json(name = "count") val count: Int = 0,
    @Json(name = "next_page_url") val nextPageUrl: String = "",
    @Json(name = "perv_page_url") val pervPageUrl: String = "",
    @Json(name = "total_pages") val totalPages: Int = 0,
    @Json(name = "current_page") val currentPage: Int = 0
)