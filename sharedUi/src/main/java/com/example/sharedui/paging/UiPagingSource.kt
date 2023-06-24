package com.example.sharedui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class UiPagingSource<T : Any>(
    var responseGetter: suspend (Int) -> Result<UiPage<T>>,
) :
    PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let {

            val closestIndex = state.closestPageToPosition(it)
            closestIndex?.prevKey?.plus(1) ?: closestIndex?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val pageIndex = params.key ?: 1
            val response = responseGetter(pageIndex).getOrElse { return LoadResult.Error(Throwable())  }
            val page = response.results
            val nextKey = if (response.totalPages <= pageIndex) null else response.page + 1
            val prevKey = if (pageIndex <= 1) null else response.page - 1
            LoadResult.Page(page, nextKey = nextKey, prevKey = prevKey)
        } catch (e: Exception) {
            throw e
        }
    }

}

