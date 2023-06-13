package com.example.tmda.presentation.movies.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import com.example.tmda.presentation.movies.moviesList.UiPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class PageProvider(
    private val coroutineScope: CoroutineScope,
    private val moviePageProvider: suspend (Int) -> MoviesPage,
    private val isMovieSavedProvider: suspend (Int) -> Boolean,

    ) {

    fun createPager(): Pager<Int, MovieUiDto> {

        return Pager(
            initialKey = 1,
            config = PagingConfig(
                20,
                prefetchDistance = 20,
                enablePlaceholders = false,


                ), pagingSourceFactory = {

                UiPagingSource(::getMovieDtoPage) })
    }

    private suspend fun getMovieDtoPage(pageIndex: Int): UiPage<MovieUiDto> {
        val moviesPage = moviePageProvider(pageIndex)
        val isSavedCorrespondingList = coroutineScope.async(Dispatchers.IO) { getIsSavedCorrespondingList(moviesPage) }.await()
        val moviesUiDtoList = moviesPage.results.mapIndexed { index, movie ->
            MovieUiDto(movie, isSavedCorrespondingList[index])
        }

        return UiPage(
            page = pageIndex, results = moviesUiDtoList,
            totalPages = moviesPage.totalPages,
            totalResults = moviesPage.totalResults,
        )
    }

    private suspend fun getIsSavedCorrespondingList(moviesPage: MoviesPage): List<Boolean> {
        return moviesPage.results.map { movie ->
            coroutineScope.async(Dispatchers.IO) {
                isMovieSavedProvider(movie.id)
            }
        }.awaitAll()
    }
}