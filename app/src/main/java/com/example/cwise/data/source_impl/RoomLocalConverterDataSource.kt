package com.example.cwise.data.source_impl

import android.database.sqlite.SQLiteFullException
import com.example.cwise.data.database.dao.CurrencyDao
import com.example.cwise.data.database.dao.RateDao
import com.example.cwise.data.database.mappers.toCurrency
import com.example.cwise.data.database.mappers.toCurrencyEntity
import com.example.cwise.data.database.mappers.toRate
import com.example.cwise.data.database.mappers.toRateEntity
import com.example.cwise.domain.model.Currency
import com.example.cwise.domain.model.Rate
import com.example.cwise.domain.source.CurrencyId
import com.example.cwise.domain.source.LocalConverterDataSource
import com.example.cwise.domain.source.RateId
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomLocalConverterDataSource @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val rateDao: RateDao
): LocalConverterDataSource {
    override fun getRates(): Flow<Rate?> {
        return rateDao.getRate().map {
            it?.toRate()
        }
    }

    override suspend fun upsertRates(rate: Rate): Result<RateId, DataError.Local> {
        return try {
            val entity = rate.toRateEntity()
            rateDao.upsertRates(entity)
            Result.Success(entity.id.toString())
        }catch (e: SQLiteFullException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRates() {
        rateDao.deleteRates()
    }

    override fun getCurrencies(): Flow<Currency?> {
        return currencyDao.getCurrencies().map {
            it?.toCurrency()
        }
    }

    override suspend fun upsertCurrency(currency: Currency): Result<CurrencyId, DataError.Local> {
        return try{
            val entity = currency.toCurrencyEntity()
            currencyDao.upsertCurrencies(entity)
            Result.Success(entity.id.toString())
        }catch (e: SQLiteFullException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteCurrency() {
        currencyDao.deleteCurrencies()
    }
}