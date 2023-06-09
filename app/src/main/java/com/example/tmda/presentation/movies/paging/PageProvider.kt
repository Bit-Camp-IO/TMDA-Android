package com.example.tmda.presentation.movies.paging

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import com.example.tmda.presentation.movies.moviesList.UiPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageProvider(
    private val coroutineScope: CoroutineScope,
    private val moviePageProvider: suspend (Int) -> MoviesPage,
    private val isMovieSavedProvider: suspend (Int) -> Boolean
) {
    private lateinit var pagingSource: UiPagingSource<MovieUiDto>
    private var cachedUiPages = mutableListOf<UiPage<MovieUiDto>>()
    private lateinit var cachedPager: Pager<Int, MovieUiDto>

    init {
        createPager()
    }

    fun getMoviesPagesStream() = cachedPager.flow.cachedIn(coroutineScope)


    fun createPager(): Pager<Int, MovieUiDto> {
        cachedPager =
            Pager(
                initialKey = 1,
                config = PagingConfig(
                    20,
                    enablePlaceholders = true,
                    initialLoadSize = 60,
                    prefetchDistance = 20
                ), pagingSourceFactory = {
                    UiPagingSource(::getMovieDtoPage)
                })

        return cachedPager
    }

    private suspend fun getMovieDtoPage(pageIndex: Int): UiPage<MovieUiDto> {
        if (pageIndex + 1 > cachedUiPages.size) {
            Log.d("xxxx", "if ")
            val moviesPage = moviePageProvider(pageIndex)
            val isSavedCorrespondingList = moviesPage.results.map { isMovieSavedProvider(it.id) }
            val moviesUiDtoList = moviesPage.results.mapIndexed { index, movie ->
                MovieUiDto(movie, isSavedCorrespondingList[index])
            }
            val uiPage = UiPage(
                page = pageIndex, results = moviesUiDtoList,
                totalPages = moviesPage.totalPages,
                totalResults = moviesPage.totalResults,
            )
            cachedUiPages.add(uiPage)
        }
        Log.d("xxxx", pageIndex.toString())
        return cachedUiPages[pageIndex]
    }


    fun invalidatePagingData() {
        val newCachedUiPage = mutableListOf<UiPage<MovieUiDto>>()
        for (page in cachedUiPages) {
            coroutineScope.launch {
                newCachedUiPage.add(
                    UiPage(
                        page.page,
                        page.results.map { it.updateIsSaved() },
                        page.totalPages,
                        page.totalResults
                    )
                )
            }
        }


        pagingSource.invalidate()

        cachedUiPages = newCachedUiPage
    }

    private fun MovieUiDto.updateIsSaved(): MovieUiDto {
        var isSaved = false
        coroutineScope.launch(Dispatchers.IO) {
            isSaved = isMovieSavedProvider(id)
        }
        return copy(isSaved = isSaved)
    }

}