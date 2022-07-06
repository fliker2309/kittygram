package com.example.kittygram.di

import com.example.kittygram.data.network.AuthInterceptor
import com.example.kittygram.data.network.CatsService
import com.example.kittygram.domain.repository.CatRepository
import com.example.kittygram.domain.usecase.CatsUseCases
import com.example.kittygram.domain.usecase.GetCatById
import com.example.kittygram.domain.usecase.GetCats
import com.example.kittygram.domain.usecase.RemoveCat
import com.example.kittygram.utils.Constants.Companion.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
        .addInterceptor(AuthInterceptor())
        .build()

    @Provides
    fun contentType() = "application/json".toMediaType()

    @Provides
    fun json() = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): CatsService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient())
            .addConverterFactory(json().asConverterFactory(contentType()))
            .build()
            .create(CatsService::class.java)

    @Provides
    @Singleton
    fun provideCatsUseCases(repository: CatRepository) : CatsUseCases{
        return CatsUseCases(
            getCats = GetCats(repository),
            getCatById = GetCatById(repository),
            removeCatFromFavorite = RemoveCat(repository),
            //todo add more use cases
        )
    }
}
