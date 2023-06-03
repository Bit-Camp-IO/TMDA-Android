package com.example.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.dto.movies.MoviesBriefWrapperDto
import com.example.movies.data.mappers.toMovie
import com.example.movies.data.remote.MoviesApiService
import com.example.movies.domain.enities.Movie

class MoviesPagingSourceWithId(
    private val apiServices: MoviesApiService,
    private val movieId: Int
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
            val response = apiServices.getRecommendMovies(movieId, pageIndex)
            val moviesBriefs = response.results
            val nextKey = if (response.totalPages <= pageIndex) null else response.page + 1
            val prevKey = if (pageIndex <= 1) null else response.page - 1
            LoadResult.Page(moviesBriefs.map { it.toMovie() }, nextKey = nextKey, prevKey = prevKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}

class MoviesPagingSource(
    private val responseGetter: suspend (Int) -> MoviesBriefWrapperDto,

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
            val moviesBriefs = response.results
            val nextKey = if (response.totalPages <= pageIndex) null else response.page + 1
            val prevKey = if (pageIndex <= 1) null else response.page - 1
            LoadResult.Page(moviesBriefs.map { it.toMovie() }, nextKey = nextKey, prevKey = prevKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}