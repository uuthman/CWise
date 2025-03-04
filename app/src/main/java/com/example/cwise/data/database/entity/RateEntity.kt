package com.example.cwise.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val base: String,
    val rates: Map<String, Double>,
    val date: String,
    val timestamp: Long
)

