package com.example.cwise.data.dto

import com.squareup.moshi.Json

data class CurrencyDto(
    @Json(name = "symbols")
    val symbols: Map<String, String>
)