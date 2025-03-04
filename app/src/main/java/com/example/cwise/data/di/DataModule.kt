package com.example.cwise.data.di

import android.content.Context
import androidx.room.Room
import com.example.cwise.BuildConfig
import com.example.cwise.data.database.ConverterDatabase
import com.example.cwise.data.database.converter.MapTypeConverter
import com.example.cwise.data.network.CWiseApi
import com.example.cwise.data.network.adapter.MapDoubleAdapter
import com.example.cwise.data.network.interceptor.ApiKeyInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val TIMEOUT = 60L

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().apply {
        add(KotlinJsonAdapterFactory())
        add(MapDoubleAdapter())
    }.build()



    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().apply {

        if(BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
        addInterceptor(
            ApiKeyInterceptor()
        )
        connectTimeout(TIMEOUT,TimeUnit.SECONDS)
        writeTimeout(TIMEOUT,TimeUnit.SECONDS)
        readTimeout(TIMEOUT,TimeUnit.SECONDS)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,moshi: Moshi): Retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        client(okHttpClient)
        addConverterFactory(
           MoshiConverterFactory.create(moshi)
        )
    }.build()


    @Provides
    @Singleton
    fun provideCWiseApiDataSource(retrofit: Retrofit): CWiseApi =
        retrofit.create(CWiseApi::class.java)


    @Provides
    @Singleton
    fun provideMapTypeConverter(): MapTypeConverter = MapTypeConverter()


    @Provides
    @Singleton
    fun provideConverterDatabase(@ApplicationContext appContext: Context,typeConverter: MapTypeConverter): ConverterDatabase =
        Room.databaseBuilder(
            appContext,
            ConverterDatabase::class.java,
            "converter.db"
        ).addTypeConverter(typeConverter).build()

    
    @Provides
    @Singleton
    fun provideRateDao(converterDatabase: ConverterDatabase) = converterDatabase.rateDao


    @Provides
    @Singleton
    fun provideCurrencyDao(converterDatabase: ConverterDatabase) = converterDatabase.currencyDao
}