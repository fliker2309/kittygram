package com.example.kittygram.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.kittygram.data.network.CatsPagingSource
import com.example.kittygram.data.network.CatsService
import com.example.kittygram.utils.Constants.Companion.DEFAULT_PAGE_SIZE

class GetCatsFromNetwork(private val service: CatsService) {

    suspend operator fun invoke(page: Int, pageSize: Int) = service.getCats(page, pageSize)
}
