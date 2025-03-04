package com.example.cwise.domain.repository

import com.example.cwise.domain.model.Rate
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface RateRepository {
    fun getRates(): Flow<Rate?>
    suspend fun fetchRates(base: String): EmptyResult<DataError>
}