package com.example.kittygram.domain.usecase

import com.example.kittygram.domain.repository.CatRepository

class GetCats(private val repository: CatRepository) {
    suspend operator fun invoke(page: Int, pageSize: Int) = repository.getCats(page, pageSize)
}
