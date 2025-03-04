package com.example.cwise.data.network

import com.example.cwise.data.response.ErrorResponse
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> safeCall(execute: () -> Response<T>): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: Exception) {
        if(e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified  T> responseToResult(response: Response<T>): Result<T, DataError.Network> {
    return when(response.isSuccessful) {
        true -> {
            val body = response.body()
            if(body != null) {
                Result.Success(body)
            } else {
                Result.Error(DataError.Network.UNKNOWN)
            }
        }
        false -> {
            val errorBody = response.errorBody()?.string()
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter = moshi.adapter(ErrorResponse::class.java)

            val errorResponse = errorBody?.takeIf { it.isNotBlank() }?.let { adapter.fromJson(it) }

            when(errorResponse?.code) {
                401 -> Result.Error(DataError.Network.UNAUTHORIZED)
                429 -> Result.Error(DataError.Network.MAXIMUM_REACHED)
                601 -> Result.Error(DataError.Network.INVALID_BASE_CURRENCY)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }
}
