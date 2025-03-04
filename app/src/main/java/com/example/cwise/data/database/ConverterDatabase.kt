package com.example.cwise.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cwise.data.database.converter.MapTypeConverter
import com.example.cwise.data.database.dao.CurrencyDao
import com.example.cwise.data.database.dao.RateDao
import com.example.cwise.data.database.entity.CurrencyEntity
import com.example.cwise.data.database.entity.RateEntity

@Database(
    entities = [
        RateEntity::class,
        CurrencyEntity::class
    ],
    version = 1
)
@TypeConverters(
    MapTypeConverter::class,
)
abstract class ConverterDatabase: RoomDatabase() {
    abstract val rateDao: RateDao
    abstract val currencyDao: CurrencyDao
}