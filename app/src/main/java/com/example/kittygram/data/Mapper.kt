package com.example.kittygram.data

import com.example.kittygram.data.network.CatDTO
import com.example.kittygram.domain.model.Cat

internal fun CatDTO.toCat(): Cat {
    return Cat(
        id = id,
        url = imageUrl
    )
}
