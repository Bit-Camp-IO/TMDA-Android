package com.example.tmda.presentation.shared.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.tmda.presentation.movies.moviesList.UiPage

class PagingProvider<T : Any>(
    private val dataPageProvider: suspend (Int) -> Result<UiPage<T>>
) {


    fun createPager(): Pager<Int, T> {
        return Pager(
            initialKey = 1,
            config = PagingConfig(
                20,
                prefetchDistance = 10,
                initialLoadSize = 20,
                enablePlaceholders = false,
            ), pagingSourceFactory = {
                UiPagingSource { dataPageProvider(it) }
            })
    }
}