package com.example.kittygram.data

import com.example.kittygram.data.db.CatDao
import com.example.kittygram.domain.model.Cat
import com.example.kittygram.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class CatRepositoryImpl(private val dao: CatDao) : CatRepository {
    override  fun getCats(page: Int, pageSize: Int): Flow<List<Cat>> {
        return dao.getAllCats()
    }

    override suspend fun getCatById(id: String): Cat? {
        return dao.getCat(id)
    }

    override suspend fun saveCat(cat: Cat) = dao.addCatToFavorite(cat)

    override suspend fun removeCat(cat: Cat) = dao.removeCatFromFavorite(cat)
}
