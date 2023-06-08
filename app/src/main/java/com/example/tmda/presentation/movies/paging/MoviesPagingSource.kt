package com.example.tmda.presentation.movies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.enities.movie.MoviesPage

class MoviesPagingSource(
    private val responseGetter: suspend (Int) -> MoviesPage,
    ) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val closestIndex = state.closestPageToPosition(it)
            closestIndex?.prevKey?.plus(1) ?: closestIndex?.nextKey?.minus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageIndex = params.key ?: 1
            val response = responseGetter(pageIndex)
            val moviesPage = response.results
            val nextKey = if (response.totalPages <= pageIndex) null else response.page + 1
            val prevKey = if (pageIndex <= 1) null else response.page - 1
            LoadResult.Page(moviesPage, nextKey = nextKey, prevKey = prevKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}