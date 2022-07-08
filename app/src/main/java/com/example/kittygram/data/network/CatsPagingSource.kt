package com.example.kittygram.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kittygram.data.toCat
import com.example.kittygram.domain.model.Cat
import java.io.IOException
import javax.inject.Inject

private const val CATS_STARTING_PAGE_INDEX = 1

class CatsPagingSource @Inject constructor(private val service: CatsService) : PagingSource<Int, Cat>() {
    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
       return try {
           val position = params.key ?: CATS_STARTING_PAGE_INDEX
           val response = service.getCats(position, params.loadSize)
           val cats = response.map { it.toCat() }
           val nextKey = if (cats.isEmpty()) null else position + 1
           LoadResult.Page(
            data = cats,
               prevKey = if (position == CATS_STARTING_PAGE_INDEX) null else position - 1,
               nextKey = nextKey
           )

       } catch (exception : IOException){
           return LoadResult.Error(exception)
       } catch (exception : Exception){
           throw IOException(exception)
       }
    }
}
