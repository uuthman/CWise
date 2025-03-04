package com.example.cwise.data.source_impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.example.cwise.data.network.CWiseApiFake
import com.example.cwise.domain.util.DataError
import com.example.cwise.domain.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RetrofitRemoteConverterDataSourceTest {
    private lateinit var apiFake: CWiseApiFake
    private lateinit var remoteConverterDataSource: RetrofitRemoteConverterDataSource

    @BeforeEach
    fun setUp() {
        apiFake = CWiseApiFake()
        remoteConverterDataSource = RetrofitRemoteConverterDataSource(
            api = apiFake
        )
    }


    @Test
    fun `getCurrencies should return MAXIMUM_REACHED when API returns 429`() = runBlocking {
        apiFake.errorResponseCode = 429

        val result = remoteConverterDataSource.getCurrencies()

        assertThat(result).isInstanceOf(Result.Error::class)

        assertThat(DataError.Network.MAXIMUM_REACHED).isEqualTo((result as Result.Error).error)
    }

    @Test
    fun `getRates should return INVALID_BASE_CURRENCY when API returns 601`() = runBlocking {
        apiFake.errorResponseCode = 601

        val result = remoteConverterDataSource.getRates("USD")
        assertThat(result).isInstanceOf(Result.Error::class)

        assertThat(DataError.Network.INVALID_BASE_CURRENCY).isEqualTo((result as Result.Error).error)
    }

}
