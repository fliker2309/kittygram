package com.example.kittygram.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kittygram.data.model.Cat

private const val CATS_STARTING_PAGE_INDEX = 1

class CatsPagingSource(private val catsService: CatsService) : PagingSource<Int, Cat>() {
    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
       return try {
           val position = params.key ?: CATS_STARTING_PAGE_INDEX
           val response = catsService.getCats(position, params.loadSize)
          return  LoadResult.Page(
              data = response.body()
          )
       }
    }
}
