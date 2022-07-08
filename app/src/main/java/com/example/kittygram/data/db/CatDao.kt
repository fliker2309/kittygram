package com.example.kittygram.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kittygram.domain.model.Cat
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Query("SELECT * FROM cats")
    fun getAllCats(): Flow<List<Cat>>

    @Query("SELECT * FROM cats WHERE id = :id")
    suspend fun getCat(id: String): Cat?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCatToFavorite(cat: Cat)

    @Delete
    suspend fun removeCatFromFavorite(cat: Cat)
}
