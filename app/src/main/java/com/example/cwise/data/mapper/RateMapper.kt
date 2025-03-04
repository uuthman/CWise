package com.example.cwise.data.mapper

import com.example.cwise.core.utils.DateUtil
import com.example.cwise.data.dto.RateDto
import com.example.cwise.domain.model.Rate

fun RateDto.toRate(): Rate {
    return Rate(
        base = base,
        rates = rates,
        date = date,
        timestamp = DateUtil.convertTimestampToDate(timestamp)
    )
}