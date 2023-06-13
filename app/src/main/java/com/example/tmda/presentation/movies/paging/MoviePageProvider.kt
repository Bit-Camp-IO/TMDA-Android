package com.example.tmda.presentation.movies.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.tmda.presentation.movies.moviesList.UiPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MoviePageProvider(
    private val coroutineScope: CoroutineScope,
    private val moviePageProvider: suspend (String, Int) -> MoviesPage,
) {
    lateinit var currentPager: Pager<Int, Movie>
        private set
    private lateinit var pagingSource: UiPagingSource<Movie>
    private var currentKeyWord = ""

    init {
        createNewPager()
    }

    private fun createNewPager() {
        currentPager = Pager(
            initialKey = 1,
            config = PagingConfig(
                20,
                prefetchDistance = 5,
                initialLoadSize = 20,
                enablePlaceholders = true,
            ), pagingSourceFactory = {
                pagingSource = UiPagingSource(::getSearchPage)
                pagingSource
            })
    }

    private suspend fun getSearchPage(page: Int) =
        coroutineScope.async(Dispatchers.IO) {
            val moviePage = moviePageProvider(currentKeyWord, page)
            UiPage(
                page = moviePage.page,
                results = moviePage.results,
                totalPages = moviePage.totalPages,
                totalResults = moviePage.totalResults
            )

        }.await()

    fun changeKeyword(keyword: String) {
        pagingSource.invalidate()
        currentKeyWord = keyword

    }

}