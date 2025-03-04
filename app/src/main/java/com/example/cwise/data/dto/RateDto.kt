package com.example.cwise.data.dto

import com.squareup.moshi.Json

data class RateDto(
    @Json(name = "base")
    val base: String = "USD",
    @Json(name = "rates")
    val rates: Map<String, Double>,
    @Json(name = "date")
    val date: String,
    @Json(name = "timestamp")
    val timestamp: Long,
)