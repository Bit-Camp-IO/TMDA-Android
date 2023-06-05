package com.example.movies.domain.repositories

import com.example.movies.data.dto.account.MovieAccountStatesDto
import com.example.movies.data.dto.credits.CreditsDto
import com.example.movies.data.dto.image.ImageCollectionDto
import com.example.movies.data.dto.movies.LatestMovieDto
import com.example.movies.data.dto.videos.VideoContainerDto
import com.example.movies.domain.enities.MovieDetails
import com.example.movies.domain.enities.MoviesPage

interface MoviesRepository {
    suspend fun getMovieDetails(id: Int): MovieDetails

    //
    suspend fun getRecommendMovies(id: Int, page: Int = 1): MoviesPage
    suspend fun getSimilarMovies(id: Int, page: Int = 1): MoviesPage
    suspend fun getNowPlayingMovies(page: Int = 1): MoviesPage
    suspend fun getPopularMovies(page: Int = 1): MoviesPage
    suspend fun getTopRatedMovies(page: Int = 1): MoviesPage
    suspend fun getUpComingMovies(page: Int = 1):MoviesPage
    suspend fun getTrendingMovies(): MoviesPage

    //
    suspend fun getMovieAccountStates(id: Int, sessionId: String): MovieAccountStatesDto
    suspend fun getMovieCredits(id: Int): CreditsDto
    suspend fun getMovieImages(id: Int, languageCode: String? = null): ImageCollectionDto
    suspend fun getLatestMovie(): LatestMovieDto
    suspend fun getMovieVideos(id: Int): VideoContainerDto
  //  fun getMoviesStream(movieId: Int): Flow<PagingData<Movie>>


//    fun getNowPlayingMoviesStream(): Flow<PagingData<Movie>>
//    fun getPopularMoviesStream(): Flow<PagingData<Movie>>
//    fun getUpComingMoviesStream(): Flow<PagingData<Movie>>
//    fun getTopRatedMoviesStream(): Flow<PagingData<Movie>>
}