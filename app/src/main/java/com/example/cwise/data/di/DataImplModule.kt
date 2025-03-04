package com.example.cwise.data.di

import com.example.cwise.data.repository_impl.CurrencyRepositoryImpl
import com.example.cwise.data.repository_impl.RateRepositoryImpl
import com.example.cwise.data.source_impl.RetrofitRemoteConverterDataSource
import com.example.cwise.data.source_impl.RoomLocalConverterDataSource
import com.example.cwise.domain.repository.CurrencyRepository
import com.example.cwise.domain.repository.RateRepository
import com.example.cwise.domain.source.LocalConverterDataSource
import com.example.cwise.domain.source.RemoteConverterDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataImplModule {

    @Binds
    @Singleton
    abstract fun bindLocalConverterDataSource(
        source: RoomLocalConverterDataSource
    ): LocalConverterDataSource


    @Binds
    @Singleton
    abstract fun bindRemoteConverterDataSource(
        source: RetrofitRemoteConverterDataSource
    ): RemoteConverterDataSource

    @Binds
    @Singleton
    abstract fun bindRateRepository(
        repository: RateRepositoryImpl
    ): RateRepository

    @Binds
    @Singleton
    abstract fun bindCurrencyRepository(
        repositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository
}