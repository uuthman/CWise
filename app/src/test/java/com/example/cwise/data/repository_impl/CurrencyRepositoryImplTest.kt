package com.example.cwise.data.repository_impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.example.cwise.data.database.CurrencyDaoFake
import com.example.cwise.data.database.RateDaoFake
import com.example.cwise.data.database.entity.CurrencyEntity
import com.example.cwise.data.mapper.toCurrency
import com.example.cwise.data.network.CWiseApiFake
import com.example.cwise.data.source_impl.RetrofitRemoteConverterDataSource
import com.example.cwise.data.source_impl.RoomLocalConverterDataSource
import com.example.cwise.data.util.MainCoroutineExtension
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
internal class CurrencyRepositoryImplTest(
    private val mainCoroutineExtension: MainCoroutineExtension
) {

    private lateinit var currencyRepositoryImpl: CurrencyRepositoryImpl
    private lateinit var currencyDaoFake: CurrencyDaoFake
    private lateinit var rateDaoFake: RateDaoFake
    private lateinit var apiFake: CWiseApiFake
    private lateinit var retrofitRemoteConverterDataSource: RetrofitRemoteConverterDataSource
    private lateinit var roomLocalConverterDataSource: RoomLocalConverterDataSource

    private lateinit var testScope: CoroutineScope

    @BeforeEach
    fun setUp() {
        testScope = CoroutineScope(mainCoroutineExtension.testDispatcher)
        currencyDaoFake = CurrencyDaoFake()
        rateDaoFake = RateDaoFake()
        apiFake = CWiseApiFake()
        retrofitRemoteConverterDataSource = RetrofitRemoteConverterDataSource(
            api = apiFake
        )
        roomLocalConverterDataSource = RoomLocalConverterDataSource(
            currencyDao = currencyDaoFake,
            rateDao = rateDaoFake
        )
        currencyRepositoryImpl = CurrencyRepositoryImpl(
            localDataSource = roomLocalConverterDataSource,
            applicationScope = testScope,
            remoteDataSource = retrofitRemoteConverterDataSource
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test sync currencies, currencies saved to database`() = runTest {
        currencyRepositoryImpl.fetchCurrencies()

        advanceUntilIdle()

        val currencies = currencyRepositoryImpl.getCurrencies()
            .first()

        assertThat(apiFake.currencies.toCurrency())
            .isEqualTo(currencies)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test should  delete old currencies before inserting new currencies`() = runTest{
        currencyDaoFake.upsertCurrencies(CurrencyEntity(currencies = mapOf("USD" to "US Dollar")))

        currencyRepositoryImpl.fetchCurrencies()

        advanceUntilIdle()

        val currencies = currencyDaoFake.getCurrencies()
            .first()

        assertThat(currencies).isNotNull()
        assertThat("AUD").isEqualTo(currencies?.currencies?.keys?.first())
    }
}