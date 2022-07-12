package com.example.kittygram.di

import android.app.Application
import androidx.room.Room
import com.example.kittygram.data.CatRepositoryImpl
import com.example.kittygram.data.db.CatDatabase
import com.example.kittygram.data.network.AuthInterceptor
import com.example.kittygram.data.network.CatsService
import com.example.kittygram.domain.repository.CatRepository
import com.example.kittygram.domain.usecase.*
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
        .addInterceptor(AuthInterceptor())
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

    @Provides
    @Singleton
    fun provideCatsUseCases(repository: CatRepository, service: CatsService): CatsUseCases {
        return CatsUseCases(
            getCats = GetCats(repository),
            getCatById = GetCatById(repository),
            removeCatFromFavorite = RemoveCat(repository),
            saveCatToFavorite = SaveCat(repository),
            getCatsFromNetwork = GetCatsFromNetwork(service),
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CatDatabase {
        return Room.databaseBuilder(
            app,
            CatDatabase::class.java,
            CatDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCatRepository(database: CatDatabase): CatRepository {
        return CatRepositoryImpl(database.catDao())
    }
}
