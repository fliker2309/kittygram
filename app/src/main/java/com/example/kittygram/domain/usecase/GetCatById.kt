package com.example.kittygram.domain.usecase

import com.example.kittygram.domain.model.Cat
import com.example.kittygram.domain.repository.CatRepository

class GetCatById(private val repository: CatRepository) {
    suspend operator fun invoke(id: String): Cat? {
        return repository.getCatById(id)
    }
}
