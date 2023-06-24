package com.example.sharedui.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig

class PagingProvider<T : Any>(
    private val dataPageProvider: suspend (Int) -> Result<UiPage<T>>
) {
    private lateinit var currentPager: Pager<Int, T>
    private lateinit var pagingSource: UiPagingSource<T>

  init {
      createNewPager()
  }
    fun createNewPager(): Pager<Int, T> {
        currentPager= Pager(
            initialKey = 1,
            config = PagingConfig(
                20,
                prefetchDistance = 10,
                initialLoadSize = 20,
                enablePlaceholders = false,
            ), pagingSourceFactory = {
                pagingSource=   UiPagingSource { dataPageProvider(it) }
                pagingSource
            })

        return currentPager
    }

    fun invalidate(){
        pagingSource.invalidate()
    }
}