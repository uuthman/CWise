package com.example.cwise.core.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtil {


    fun convertTimestampToDate(timestamp: Long, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return date.format(formatter)
    }

    fun convertDateStringToTimestamp(dateString: String, pattern: String = "yyyy-MM-dd HH:mm:ss"): Long {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val dateTime = LocalDateTime.parse(dateString, formatter)
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

}