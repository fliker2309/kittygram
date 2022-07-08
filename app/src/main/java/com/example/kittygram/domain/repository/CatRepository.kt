package com.example.kittygram.domain.repository

import com.example.kittygram.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCats(
        page: Int,
        pageSize: Int
    ): Flow<List<Cat>>

    suspend fun getCatById(id: String): Cat?

    suspend fun saveCat(cat: Cat)

    suspend fun removeCat(cat: Cat)
}
