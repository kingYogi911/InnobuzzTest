package com.yogi.innobuzztest.di

import com.google.gson.Gson
import com.yogi.innobuzztest.retrofit.NetworkDataSource
import com.yogi.innobuzztest.utils.Constants.BASE_URL_MAIN_API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesOkHttpClientMain(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
        }.build()
    }

    @Provides
    fun providesRetrofitMain(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_MAIN_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesNetworkDataSource(retrofit: Retrofit): NetworkDataSource {
        return retrofit.create(NetworkDataSource::class.java)
    }

}