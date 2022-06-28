package com.example.kittygram.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_table")
data class Cat(
    @PrimaryKey
    val id: String,
    val breeds: List<Any>,
    val categories: List<Category>,
    val height: Int,
    val url: String,
    val width: Int
)
