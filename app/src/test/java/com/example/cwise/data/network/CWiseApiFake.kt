package com.example.cwise.data.network

import com.example.cwise.data.dto.CurrencyDto
import com.example.cwise.data.dto.RateDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class CWiseApiFake: CWiseApi {

    var errorResponseCode: Int? = null

    var rate = RateDto(
        base = "EUR",
        rates = mapOf(
            "AUD" to 1.566015,
            "CAD" to 1.560132,
            "CHF" to 1.154727,
            "CNY" to 7.827874,
            "GBP" to 0.882047
        ),
        date = "2025-03-03",
        timestamp = 1519296206
    )

    var currencies = CurrencyDto(
        symbols = mapOf(
            "AUD" to "Australian Dollar",
            "CAD" to "Canadian Dollar",
            "CHF" to "Swiss Franc",
            "CNY" to "Chinese Yuan",
            "GBP" to "British Pound Sterling"
        )
    )

    override suspend fun getRates(base: String): Response<RateDto> {
        return if(errorResponseCode != null) {
            Response.error(errorResponseCode!!, createErrorBody(errorResponseCode!!))
        } else {
            Response.success(rate)
        }
    }

    override suspend fun getCurrencies(): Response<CurrencyDto> {
        return if (errorResponseCode != null) {
            Response.error(errorResponseCode!!, createErrorBody(errorResponseCode!!))
        } else {
            Response.success(currencies)
        }
    }

    private fun createErrorBody(code: Int): ResponseBody {
        val errorJson = """{ "code": $code, "info": "Simulated API error" }"""
        return errorJson.toResponseBody("application/json".toMediaTypeOrNull())
    }
}