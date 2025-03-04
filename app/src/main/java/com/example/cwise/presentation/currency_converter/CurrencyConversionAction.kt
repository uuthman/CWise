package com.example.cwise.presentation.currency_converter

sealed interface CurrencyConversionAction {
    data class OnCurrencyClick(val currencyCode: String): CurrencyConversionAction
}