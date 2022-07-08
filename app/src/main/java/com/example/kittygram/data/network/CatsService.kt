package com.example.kittygram.data.network

import com.example.kittygram.utils.Constants.Companion.DEFAULT_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatsService {

    @GET("images/search")
    suspend fun getCats(
        @Query("page") page: Int,
        @Query("limit") pageSize: Int = DEFAULT_PAGE_SIZE
    ): List<CatDTO>

    @GET("images/{image_id}")
    suspend fun getCatById(@Path("image_id") id: String): CatDTO
}
