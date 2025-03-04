package com.example.cwise.core.di

import android.content.Context
import com.example.cwise.core.utils.ConnectivityManagerNetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideConnectivityManagerNetworkMonitor(@ApplicationContext appContext: Context): ConnectivityManagerNetworkMonitor =
        ConnectivityManagerNetworkMonitor(appContext)
}