package com.example.moviesComponent.data.repositories

import com.example.moviesComponent.data.dto.image.ImageCollectionDto
import com.example.moviesComponent.data.dto.movies.LatestMovieDto
import com.example.moviesComponent.data.local.MoviesDao
import com.example.moviesComponent.data.mappers.makePostMovieToWatchListBody
import com.example.moviesComponent.data.mappers.toMovie
import com.example.moviesComponent.data.mappers.toMovieDetails
import com.example.moviesComponent.data.mappers.toMoviePage
import com.example.moviesComponent.data.remote.MoviesApiService
import com.example.moviesComponent.domain.enities.movie.Movie
import com.example.moviesComponent.domain.enities.movie.MovieDetails
import com.example.moviesComponent.domain.enities.movie.MoviesPage
import com.example.moviesComponent.domain.repositories.MoviesRepository
import com.example.sharedComponent.dto.videos.VideoContainerDto
import com.example.sharedComponent.entities.credits.Credits
import com.example.sharedComponent.entities.people.PeoplePage
import com.example.sharedComponent.entities.people.PersonDetails
import com.example.sharedComponent.entities.review.Review
import com.example.sharedComponent.mappers.toCredits
import com.example.sharedComponent.mappers.toPeoplePage
import com.example.sharedComponent.mappers.toPersonDetails
import com.example.sharedComponent.mappers.toReview
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService,
    private val moviesDao: MoviesDao
) : MoviesRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return moviesApiService.getMovieDetails(movieId).toMovieDetails()
    }

    override suspend fun getMovieSavedState(
        movieId: Int,
        sessionId: String
    ): Boolean {
        return moviesApiService.getMovieAccountStates(movieId, sessionId).watchList
    }

    override suspend fun getMovieCredits(movieId: Int): Credits {
        return moviesApiService.getMovieCredits(movieId).toCredits()

    }

    override suspend fun getMovieImages(movieId: Int, languageCode: String?): ImageCollectionDto {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestMovie(): LatestMovieDto {
        TODO("Not yet implemented")
    }

    override suspend fun getRecommendMovies(movieId: Int, page: Int): MoviesPage {
        return moviesApiService.getRecommendMovies(movieId, page).toMoviePage()
    }

    override suspend fun getSimilarMovies(movieId: Int, page: Int): MoviesPage {
        return moviesApiService.getSimilarMovies(movieId, page).toMoviePage()
    }

    override suspend fun getNowPlayingMovies(page: Int): MoviesPage {
        return moviesApiService.getNowPlayingMovies(page).toMoviePage()
    }

    override suspend fun getPopularMovies(page: Int): MoviesPage {

        return moviesApiService.getPopularMovies(page).toMoviePage()
    }

    override suspend fun getTopRatedMovies(page: Int): MoviesPage {
        return moviesApiService.getTopRatedMovies(page).toMoviePage()
    }

    override suspend fun getUpComingMovies(page: Int): MoviesPage {
        return moviesApiService.getUpComingMovies(page).toMoviePage()
    }

    override suspend fun getTrendingMovies(): MoviesPage {
        return moviesApiService.getTrendingMovies().toMoviePage()
    }

    override suspend fun getMovieReviews(movieId: Int): List<Review> {
        return moviesApiService.getMovieReviews(movieId).results.map { it.toReview() }
    }

    override suspend fun getMovieVideos(movieId: Int): VideoContainerDto {
        return moviesApiService.getMovieVideos(movieId)
    }

    override suspend fun addMovieToWatchList(
        accountId: Int,
        sessionId: String,
        movieId: Int,
        isSaveRequest: Boolean
    ) {
        val body = makePostMovieToWatchListBody(movieId, isSaveRequest)
        return moviesApiService.postMovieToWatchList(accountId, sessionId, body)
    }

    override suspend fun searchMovies(
        keyword: String,
        includeAdults: Boolean,
        page: Int
    ): MoviesPage {
        return moviesApiService.searchMovies(keyword, includeAdults, page).toMoviePage()
    }

    override suspend fun getPersonDetails(personId: Int): PersonDetails {
        return moviesApiService.getPersonDetails(personId).toPersonDetails()
    }

    override suspend fun getPersonMovies(personId: Int): List<Movie> {
        return moviesApiService.getPersonMovies(personId).cast.map { it.toMovie() }
    }

    override suspend fun searchPeople(keyword: String, page: Int): PeoplePage {
        return moviesApiService.searchPeople(keyword, page).toPeoplePage()
    }

    override suspend fun getBookMarkedMovies(
        accountId: Int,
        sessionId: String,
        page: Int
    ): MoviesPage {
        return moviesApiService.getBookMarkedMovies(accountId, sessionId, page).toMoviePage()
    }

}