package com.example.kittygram.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kittygram.domain.model.Cat

@Database(
    entities = [Cat::class],
    version = 1
)

abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao

    companion object {
        const val DATABASE_NAME = "cats_db"
    }
}
