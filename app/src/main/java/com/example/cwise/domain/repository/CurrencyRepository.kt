package com.example.cwise.domain.repository

import com.example.cwise.domain.model.Currency
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencies(): Flow<Currency?>
    suspend fun fetchCurrencies(): EmptyResult<DataError>
}