package com.example.cwise.data.repository_impl

import com.example.cwise.data.source_impl.RetrofitRemoteConverterDataSource
import com.example.cwise.domain.model.Currency
import com.example.cwise.domain.repository.CurrencyRepository
import com.example.cwise.domain.source.LocalConverterDataSource
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.EmptyResult
import com.example.cwise.domain.util.Result
import com.example.cwise.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val localDataSource: LocalConverterDataSource,
    private val applicationScope: CoroutineScope,
    private val remoteDataSource: RetrofitRemoteConverterDataSource
): CurrencyRepository {
    override fun getCurrencies(): Flow<Currency?> {
        return localDataSource.getCurrencies()
    }

    override suspend fun fetchCurrencies(): EmptyResult<DataError> {
        return when(val result = remoteDataSource.getCurrencies()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.deleteCurrency()
                    localDataSource.upsertCurrency(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }
}