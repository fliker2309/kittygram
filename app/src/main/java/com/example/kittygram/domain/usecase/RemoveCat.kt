package com.example.kittygram.domain.usecase

import com.example.kittygram.domain.model.Cat
import com.example.kittygram.domain.repository.CatRepository

class RemoveCat(private val repository: CatRepository) {
    suspend operator fun invoke(cat: Cat) = repository.removeCat(cat)
}
