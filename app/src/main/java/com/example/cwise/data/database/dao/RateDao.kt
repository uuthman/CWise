package com.example.cwise.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.cwise.data.database.entity.RateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {

    @Upsert
    suspend fun upsertRates(rateEntity: RateEntity)

    @Query("DELETE FROM rateentity")
    suspend fun deleteRates()

    @Query("SELECT * FROM rateentity")
    fun getRate(): Flow<RateEntity?>
}