package com.example.kittygram.di

import com.example.kittygram.domain.repository.CatRepository
import com.example.kittygram.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // todo запровайдить репозиторий и получить используемые им интерфейсы

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
