package com.example.cwise.data.mapper

import com.example.cwise.data.dto.CurrencyDto
import com.example.cwise.domain.model.Currency

fun CurrencyDto.toCurrency(): Currency {
    return Currency(
        currencies = symbols
    )
}