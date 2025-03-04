package com.example.cwise.data.network.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class MapDoubleAdapter {
    @FromJson
    fun fromJson(json: Map<String, Double>): Map<String, Double> = json

    @ToJson
    fun toJson(map: Map<String, Double>): Map<String, Double> = map
}