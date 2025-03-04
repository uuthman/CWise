package com.example.cwise.data.repository_impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.example.cwise.core.utils.Constants
import com.example.cwise.data.database.CurrencyDaoFake
import com.example.cwise.data.database.RateDaoFake
import com.example.cwise.data.database.entity.RateEntity
import com.example.cwise.data.mapper.toRate
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
internal class RateRepositoryImplTest(
    private val mainCoroutineExtension: MainCoroutineExtension
) {

    private lateinit var rateRepositoryImpl: RateRepositoryImpl
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
        rateRepositoryImpl = RateRepositoryImpl(
            localDataSource = roomLocalConverterDataSource,
            applicationScope = testScope,
            remoteDataSource = retrofitRemoteConverterDataSource
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test sync rates, rates saved to database`() = runTest {
        rateRepositoryImpl.fetchRates(Constants.BASE_CURRENCY)

        advanceUntilIdle()

        val rates = rateRepositoryImpl.getRates().first()

        assertThat(apiFake.rate.toRate())
            .isEqualTo(rates)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test should delete old rates before inserting new rates`() = runTest{
        rateDaoFake.upsertRates(RateEntity(
            base = "EUR",
            rates = mapOf(
                "USD" to 1.566
            ),
            date = "2025-01-03",
            timestamp = 1513496206
        ))

        rateRepositoryImpl.fetchRates(Constants.BASE_CURRENCY)

        advanceUntilIdle()

        val rates = rateDaoFake.getRate().first()

        assertThat(rates).isNotNull()
        assertThat("AUD").isEqualTo(rates?.rates?.keys?.first())
        assertThat(1.566015).isEqualTo(rates?.rates?.values?.first())
    }




}
