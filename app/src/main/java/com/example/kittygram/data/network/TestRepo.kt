package com.example.kittygram.data.network

import javax.inject.Inject

class TestRepo @Inject constructor(private val service: CatsService) {

    suspend fun getCats() = service.getCats()
}
