package com.example.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movies.data.dto.account.MovieAccountStatesDto
import com.example.movies.data.dto.credits.CreditsDto
import com.example.movies.data.dto.image.ImageCollectionDto
import com.example.movies.data.dto.movies.LatestMovieDto
import com.example.movies.data.dto.videos.VideoContainerDto
import com.example.movies.data.local.MoviesDao
import com.example.movies.data.mappers.toMovie
import com.example.movies.data.mappers.toMovieDetails
import com.example.movies.data.paging.MoviesPagingSource
import com.example.movies.data.paging.MoviesPagingSourceWithId
import com.example.movies.data.remote.MoviesApiService
import com.example.movies.domain.enities.Movie
import com.example.movies.domain.enities.MovieDetails
import com.example.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService,
    private val moviesDao: MoviesDao
) : MoviesRepository {
    override suspend fun getMovieDetails(id: Int): MovieDetails {
        return moviesApiService.getMovieDetails(id).toMovieDetails()
    }

    override suspend fun getMovieAccountStates(id: Int, sessionId: String): MovieAccountStatesDto {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieCredits(id: Int): CreditsDto {
        TODO("Not yet implemented")

    }

    override suspend fun getMovieImages(id: Int, languageCode: String?): ImageCollectionDto {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestMovie(): LatestMovieDto {
        TODO("Not yet implemented")
    }

    override suspend fun getRecommendMovies(id: Int, page: Int): List<Movie> {
        return moviesApiService.getRecommendMovies(id, page).results.map { it.toMovie() }
    }

    override suspend fun getSimilarMovies(id: Int, page: Int): List<Movie> {
        return moviesApiService.getSimilarMovies(id, page).results.map { it.toMovie() }
    }

    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        return moviesApiService.getNowPlayingMovies(page).results.map { it.toMovie() }
    }

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return moviesApiService.getPopularMovies(page).results.map { it.toMovie() }
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        return moviesApiService.getTopRatedMovies(page).results.map { it.toMovie() }
    }

    override suspend fun getUpComingMovies(page: Int): List<Movie> {
        return moviesApiService.getUpComingMovies(page).results.map { it.toMovie() }
    }

    override suspend fun getTrendingMovies(): List<Movie> {
        return moviesApiService.getTrendingMovies().results.map { it.toMovie() }
    }

    override suspend fun getMovieVideos(id: Int): VideoContainerDto {
        return moviesApiService.getMovieVideos(id)
    }


    override fun getMoviesStream(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {
                MoviesPagingSourceWithId(
                    apiServices = moviesApiService,
                    movieId
                )
            }
        ).flow
    }

    override fun getNowPlayingMoviesStream(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(moviesApiService::getNowPlayingMovies) }
        ).flow
    }

    override fun getPopularMoviesStream(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(moviesApiService::getPopularMovies) }
        ).flow
    }

    override fun getUpComingMoviesStream(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(moviesApiService::getUpComingMovies) }
        ).flow
    }

    override fun getTopRatedMoviesStream(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(moviesApiService::getTopRatedMovies) }
        ).flow
    }


}