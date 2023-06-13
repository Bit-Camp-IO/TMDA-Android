package com.bitIO.tvshowcomponent.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.bitIO.tvshowcomponent.data.remote.response.BaseTvShowResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDto
import java.io.IOException

private const val INITIAL_KEY = 1

class TvShowsPagingSource(private val request: suspend (Int) -> BaseTvShowResponse) : PagingSource<Int, TvShowDto>() {
    override fun getRefreshKey(state: PagingState<Int, TvShowDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1) ?:
            state.closestPageToPosition(it)?.prevKey?.plus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowDto> {
        val key = params.key ?: INITIAL_KEY
        return try {
            val response = request(key)
            val next = if(key >= response.totalPages!!) null else key + 1
            Page(
                data = response.results!!,
                prevKey = null,
                nextKey = next
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        }

    }
}