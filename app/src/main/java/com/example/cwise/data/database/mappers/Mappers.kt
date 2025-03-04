package com.example.cwise.data.database.mappers

import com.example.cwise.core.utils.DateUtil
import com.example.cwise.data.database.entity.CurrencyEntity
import com.example.cwise.data.database.entity.RateEntity
import com.example.cwise.domain.model.Currency
import com.example.cwise.domain.model.Rate

fun RateEntity.toRate(): Rate {
    return Rate(
        base = base,
        rates = rates,
        date = date,
        timestamp = DateUtil.convertTimestampToDate(timestamp)
    )
}

fun Rate.toRateEntity(): RateEntity {
    return RateEntity(
        base = base,
        rates = rates,
        date = date,
        timestamp = DateUtil.convertDateStringToTimestamp(timestamp)
    )
}

fun CurrencyEntity.toCurrency(): Currency {
    return Currency(
        currencies = currencies
    )
}

fun Currency.toCurrencyEntity(): CurrencyEntity {
    return CurrencyEntity(
        currencies = currencies
    )
}