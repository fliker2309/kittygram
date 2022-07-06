package com.example.kittygram.domain.usecase

import com.example.kittygram.domain.model.Cat
import com.example.kittygram.domain.repository.CatRepository

class SaveCat(private val catRepository: CatRepository) {
    suspend operator fun invoke(cat: Cat) = catRepository.saveCat(cat)
}
