package com.example.kittygram.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class Cat(
    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: String,
    @ColumnInfo(name = "ImageUrl")
    val url: String
) {
    var isLiked: Boolean = false
}
