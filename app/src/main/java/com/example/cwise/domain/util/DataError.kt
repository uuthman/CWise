package com.example.cwise.domain.util

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
        MAXIMUM_REACHED,
        INVALID_BASE_CURRENCY
    }

    enum class Local: DataError {
        DISK_FULL
    }
}