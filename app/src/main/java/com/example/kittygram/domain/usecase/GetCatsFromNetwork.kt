package com.example.kittygram.domain.usecase

import com.example.kittygram.data.network.CatsService

class GetCatsFromNetwork(private val service: CatsService) {
    suspend operator fun invoke(page: Int, pageSize: Int) = service.getCats(page, pageSize)
}
