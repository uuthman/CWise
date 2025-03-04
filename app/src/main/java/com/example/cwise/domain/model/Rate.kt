package com.example.cwise.domain.model

data class Rate(
    val base: String = "USD",
    val rates: Map<String, Double>,
    val date: String,
    val timestamp: String,
)
