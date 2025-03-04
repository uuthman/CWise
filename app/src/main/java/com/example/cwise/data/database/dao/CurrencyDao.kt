package com.example.cwise.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.cwise.data.database.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    suspend fun upsertCurrencies(currencyEntity: CurrencyEntity)

    @Query("SELECT * FROM currencyentity")
    fun getCurrencies(): Flow<CurrencyEntity?>

    @Query("DELETE FROM currencyentity")
    suspend fun deleteCurrencies()
}