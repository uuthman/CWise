package com.example.cwise.presentation.currency_converter

import androidx.compose.foundation.text.input.TextFieldState
import com.example.cwise.presentation.model.CurrencyUi

data class CurrencyConverterState(
    val baseTextField: TextFieldState = TextFieldState("0"),
    val currencies: List<CurrencyUi> = emptyList(),
    val exchangeRates: Map<String, Double> = emptyMap(),
    val convertedAmount: Double = 0.0,
    val selectedTargetCurrency: String = "USD"
)
