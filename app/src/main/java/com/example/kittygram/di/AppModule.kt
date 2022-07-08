package com.example.kittygram.di

import com.example.kittygram.data.network.AuthInterceptor
import com.example.kittygram.data.network.CatsService
import com.example.kittygram.domain.repository.CatRepository
import com.example.kittygram.domain.usecase.*
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
 //todo запровайдить репозиторий и получить используемые им интерфейсы
    
    @Provides
    @Singleton
    fun provideCatsUseCases(repository: CatRepository): CatsUseCases {
        return CatsUseCases(
            getCats = GetCats(repository),
            getCatById = GetCatById(repository),
            removeCatFromFavorite = RemoveCat(repository),
            saveCatToFavorite = SaveCat(repository)
        )
    }
}
