package com.example.cwise.data.repository_impl

import com.example.cwise.data.network.CWiseApi
import com.example.cwise.data.source_impl.RetrofitRemoteConverterDataSource
import com.example.cwise.domain.model.Rate
import com.example.cwise.domain.repository.RateRepository
import com.example.cwise.domain.source.LocalConverterDataSource
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.EmptyResult
import com.example.cwise.domain.util.Result
import com.example.cwise.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val localDataSource: LocalConverterDataSource,
    private val applicationScope: CoroutineScope,
    private val remoteDataSource: RetrofitRemoteConverterDataSource
): RateRepository {
    override fun getRates(): Flow<Rate?> {
       return localDataSource.getRates()
    }

    override suspend fun fetchRates(base: String): EmptyResult<DataError> {
        return when(val result = remoteDataSource.getRates(base)) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.deleteRates()
                    localDataSource.upsertRates(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }
}