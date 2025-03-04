package com.example.cwise.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val currencies: Map<String, String>
)
