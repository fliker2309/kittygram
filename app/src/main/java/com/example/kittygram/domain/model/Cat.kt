package com.example.kittygram.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class Cat(
    @PrimaryKey
    val id: String,
    val url: String
) {
    var isLiked: Boolean = false
}
