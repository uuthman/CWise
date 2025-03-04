package com.example.cwise.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


@ProvidedTypeConverter
class MapTypeConverter {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @OptIn(ExperimentalStdlibApi::class)
    private val stringMapAdapter = moshi.adapter<Map<String, String>>().lenient()
    @OptIn(ExperimentalStdlibApi::class)
    private val doubleMapAdapter = moshi.adapter<Map<String, Double>>().lenient()


    @TypeConverter
    fun fromMap(map: Map<String, String>): String {
        return stringMapAdapter.toJson(map)
    }

    @TypeConverter
    fun toMap(json: String): Map<String, String> {
        return stringMapAdapter.fromJson(json) ?: emptyMap()
    }

    @TypeConverter
    fun fromExchangeRateMap(map: Map<String, Double>): String {
        return doubleMapAdapter.toJson(map)
    }

    @TypeConverter
    fun toExchangeRateMap(json: String): Map<String, Double> {
        return doubleMapAdapter.fromJson(json) ?: emptyMap()
    }
}