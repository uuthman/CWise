package com.example.cwise.data.database

import com.example.cwise.data.database.dao.CurrencyDao
import com.example.cwise.data.database.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class CurrencyDaoFake: CurrencyDao {

    private val currency = MutableStateFlow<CurrencyEntity?>(value = null)

    override suspend fun upsertCurrencies(currencyEntity: CurrencyEntity) {
        currency.value = currencyEntity
    }

    override fun getCurrencies(): Flow<CurrencyEntity?> {
        return currency.map {
            it
        }
    }

    override suspend fun deleteCurrencies() {
        currency.value = null
    }
}