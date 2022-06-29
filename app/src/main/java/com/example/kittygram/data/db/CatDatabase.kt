package com.example.kittygram.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao

    companion object {
        @Volatile
        private var instance: CatDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CatDatabase::class.java,
                "cats_database"
            )
                .build()
    }
}
