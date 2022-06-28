package com.example.kittygram.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kittygram.data.model.Cat
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Query("SELECT * FROM cat_table")
    suspend fun getAllCats(): LiveData<List<Cat>>

    @Query("SELECT * FROM cat_table WHERE id = :id")
    suspend fun getCat(id: String): Cat?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCatToFavorite(cat: Cat)

    @Delete
    suspend fun removeCatFromFavorite(cat: Cat)
}
