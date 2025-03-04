package com.example.cwise.presentation.mapper

import com.example.cwise.domain.model.Currency
import com.example.cwise.presentation.model.CurrencyUi

fun Currency.toCurrencyUi():List<CurrencyUi>{
    return currencies.map {
        CurrencyUi(it.key,it.value)
    }
}