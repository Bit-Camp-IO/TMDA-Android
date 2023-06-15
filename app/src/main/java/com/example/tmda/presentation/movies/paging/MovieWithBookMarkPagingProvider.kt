package com.example.tmda.presentation.movies.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import com.example.tmda.presentation.movies.moviesList.UiPage
import com.example.tmda.presentation.shared.paging.UiPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class MovieWithBookMarkPagingProvider(
    private val coroutineScope: CoroutineScope,
    private val moviePageProvider: suspend (Int) -> Result<MoviesPage>,
    private val isMovieSavedProvider: suspend (Int) -> Boolean = { false },
) {

    fun createPager(): Pager<Int, MovieUiDto> {
        return Pager(
            initialKey = 1,
            config = PagingConfig(
                20,
                prefetchDistance = 10,
                initialLoadSize = 20,
                enablePlaceholders = false,
            ), pagingSourceFactory = {
                UiPagingSource(::getMovieDtoPage)
            })
    }

    private suspend fun getMovieDtoPage(pageIndex: Int):Result< UiPage<MovieUiDto>> {

        val moviesPage = coroutineScope.async { moviePageProvider(pageIndex) }.await().getOrNull()
        val isSavedCorrespondingList =
            coroutineScope.async(Dispatchers.IO) { getIsSavedCorrespondingList(moviesPage) }.await()
                ?: return  Result.failure(Throwable())
//        UiPage(
//                    page = pageIndex,
//                    results = listOf(),
//                    totalPages = Int.MAX_VALUE,
//                    isError = true
//                )

        val moviesUiDtoList = moviesPage!!.results.mapIndexed { index, movie ->
            MovieUiDto(movie, isSavedCorrespondingList[index])
        }
        return Result.success( UiPage(
            page = pageIndex, results = moviesUiDtoList,
            totalPages = moviesPage.totalPages,
            isError = moviesPage.totalPages == -1
        ))
    }
    private suspend fun getIsSavedCorrespondingList(moviesPage: MoviesPage?): List<Boolean>? {
        return moviesPage?.results?.map { movie ->
            coroutineScope.async(Dispatchers.IO) {
                isMovieSavedProvider(movie.id)
            }
        }?.awaitAll()

    }
}