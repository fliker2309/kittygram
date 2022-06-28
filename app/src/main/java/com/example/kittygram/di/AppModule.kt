package com.example.kittygram.di

import com.example.kittygram.data.network.CatsService
import com.example.kittygram.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun baseUrl() = BASE_URL

    @Provides
    fun loggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): CatsService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatsService::class.java)
}
