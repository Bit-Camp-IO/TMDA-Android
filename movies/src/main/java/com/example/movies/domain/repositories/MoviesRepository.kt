package com.example.movies.domain.repositories

import com.example.movies.data.dto.image.ImageCollectionDto
import com.example.movies.data.dto.movies.LatestMovieDto
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.enities.movie.MovieDetails
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.shared.dto.videos.VideoContainerDto
import com.example.shared.entities.credits.Credits
import com.example.shared.entities.people.PeoplePage
import com.example.shared.entities.people.PersonDetails
import com.example.shared.entities.review.Review


interface MoviesRepository {
    suspend fun getMovieDetails(movieId: Int): MovieDetails

    //
    suspend fun getRecommendMovies(movieId: Int, page: Int = 1): MoviesPage
    suspend fun getSimilarMovies(movieId: Int, page: Int = 1): MoviesPage
    suspend fun getNowPlayingMovies(page: Int = 1): MoviesPage
    suspend fun getPopularMovies(page: Int = 1): MoviesPage
    suspend fun getTopRatedMovies(page: Int = 1): MoviesPage
    suspend fun getUpComingMovies(page: Int = 1): MoviesPage
    suspend fun getTrendingMovies(): MoviesPage
    suspend fun getMovieReviews(movieId: Int): List<Review>

    //
    suspend fun getMovieSavedState(movieId: Int, sessionId: String): Boolean
    suspend fun getMovieCredits(movieId: Int): Credits
    suspend fun getMovieImages(movieId: Int, languageCode: String? = null): ImageCollectionDto
    suspend fun getLatestMovie(): LatestMovieDto
    suspend fun getMovieVideos(movieId: Int): VideoContainerDto
    suspend fun addMovieToWatchList(
        accountId: Int,
        sessionId: String,
        movieId: Int,
        isSaveRequest: Boolean
    )

    suspend fun searchMovies(
        keyword: String,
        includeAdults: Boolean = true,
        page: Int
    ): MoviesPage

    suspend fun getPersonDetails(personId: Int): PersonDetails
    suspend fun getPersonMovies(personId: Int): List<Movie>
    suspend fun searchPeople(keyword: String, page: Int): PeoplePage
    suspend fun getBookMarkedMovies(accountId: Int,sessionId: String, page: Int): MoviesPage
}