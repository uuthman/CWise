package com.example.cwise.data.source_impl

import com.example.cwise.data.dto.CurrencyDto
import com.example.cwise.data.dto.RateDto
import com.example.cwise.data.mapper.toCurrency
import com.example.cwise.data.mapper.toRate
import com.example.cwise.data.network.CWiseApi
import com.example.cwise.data.network.safeCall
import com.example.cwise.domain.model.Currency
import com.example.cwise.domain.model.Rate
import com.example.cwise.domain.source.RemoteConverterDataSource
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.Result
import com.example.cwise.domain.util.map
import javax.inject.Inject

class RetrofitRemoteConverterDataSource @Inject constructor(
    private val api: CWiseApi
): RemoteConverterDataSource {
    override suspend fun getRates(base: String): Result<Rate, DataError.Network> {
        val result = safeCall<RateDto> {
            api.getRates(base)
        }

        return result.map { it.toRate() }
    }

    override suspend fun getCurrencies(): Result<Currency, DataError.Network> {
        val result = safeCall<CurrencyDto> {
            api.getCurrencies()
        }

        return result.map { it.toCurrency() }
    }
}