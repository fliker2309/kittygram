package com.example.kittygram.domain.repository

import com.example.kittygram.data.model.Cat

interface CatRepository {
    suspend fun getCats(
        page: Int,
        pageSize: Int
    ): List<Cat>

    suspend fun getCatById(id: String): Cat?

    suspend fun saveCat(cat: Cat)

    suspend fun removeCat(cat: Cat)
}
