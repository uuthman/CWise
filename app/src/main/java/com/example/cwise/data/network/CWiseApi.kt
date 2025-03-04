package com.example.cwise.data.network

import com.example.cwise.data.dto.CurrencyDto
import com.example.cwise.data.dto.RateDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CWiseApi {
    @GET("api/latest")
    suspend fun getRates(@Query("base") base: String): Response<RateDto>

    @GET("api/symbols")
    suspend fun getCurrencies(): Response<CurrencyDto>
}