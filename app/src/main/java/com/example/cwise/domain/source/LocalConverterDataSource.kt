package com.example.cwise.domain.source

import com.example.cwise.domain.model.Currency
import com.example.cwise.domain.model.Rate
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias RateId = String
typealias CurrencyId = String

interface LocalConverterDataSource {
    fun getRates(): Flow<Rate?>
    suspend fun upsertRates(rate: Rate): Result<RateId,DataError.Local>
    suspend fun deleteRates()

    fun getCurrencies(): Flow<Currency?>
    suspend fun upsertCurrency(currency: Currency):  Result<CurrencyId,DataError.Local>
    suspend fun deleteCurrency()
}