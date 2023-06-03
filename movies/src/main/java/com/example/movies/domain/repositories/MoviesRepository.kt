package com.example.movies.domain.repositories

import androidx.paging.PagingData
import com.example.movies.data.dto.account.MovieAccountStatesDto
import com.example.movies.data.dto.credits.CreditsDto
import com.example.movies.data.dto.image.ImageCollectionDto
import com.example.movies.data.dto.movies.LatestMovieDto
import com.example.movies.data.dto.videos.VideoContainerDto
import com.example.movies.domain.enities.Movie
import com.example.movies.domain.enities.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getMovieDetails(id: Int): MovieDetails

    //
    suspend fun getRecommendMovies(id: Int, page: Int = 1): List<Movie>
    suspend fun getSimilarMovies(id: Int, page: Int = 1): List<Movie>
    suspend fun getNowPlayingMovies(page: Int = 1): List<Movie>
    suspend fun getPopularMovies(page: Int = 1): List<Movie>
    suspend fun getTopRatedMovies(page: Int = 1): List<Movie>
    suspend fun getUpComingMovies(page: Int = 1): List<Movie>
    suspend fun getTrendingMovies(): List<Movie>

    //
    suspend fun getMovieAccountStates(id: Int, sessionId: String): MovieAccountStatesDto
    suspend fun getMovieCredits(id: Int): CreditsDto
    suspend fun getMovieImages(id: Int, languageCode: String? = null): ImageCollectionDto
    suspend fun getLatestMovie(): LatestMovieDto
    suspend fun getMovieVideos(id: Int): VideoContainerDto
    fun getMoviesStream(movieId: Int): Flow<PagingData<Movie>>


    fun getNowPlayingMoviesStream(movieId: Int): Flow<PagingData<Movie>>
    fun getPopularMoviesStream(movieId: Int): Flow<PagingData<Movie>>
    fun getUpComingMoviesStream(movieId: Int): Flow<PagingData<Movie>>
    fun getTopRatedMoviesStream(movieId: Int): Flow<PagingData<Movie>>
}