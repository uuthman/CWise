package com.example.cwise.data.database

import com.example.cwise.data.database.dao.RateDao
import com.example.cwise.data.database.entity.RateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class RateDaoFake: RateDao {

    private var rates = MutableStateFlow<RateEntity?>(value = null)

    override suspend fun upsertRates(rateEntity: RateEntity) {
        rates.value = rateEntity
    }

    override suspend fun deleteRates() {
       rates.value = null
    }

    override fun getRate(): Flow<RateEntity?> {
        return rates.map {
            it
        }
    }
}