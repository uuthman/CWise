package com.example.cwise.domain.source

import com.example.cwise.domain.model.Currency
import com.example.cwise.domain.model.Rate
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.Result

interface RemoteConverterDataSource {
    suspend fun getRates(base: String): Result<Rate, DataError.Network>
    suspend fun getCurrencies(): Result<Currency, DataError.Network>
}