package com.example.movies.data.remote

import com.example.movies.domain.enities.movies.LatestMovie
import com.example.movies.domain.enities.MovieAccountStates
import com.example.movies.domain.enities.credits.Credits
import com.example.movies.domain.enities.movies.MovieDetails
import com.example.movies.domain.enities.image.ImageCollection
import com.example.movies.domain.enities.movies.MoviesCollections
import com.example.movies.domain.enities.videos.VideoContainer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): MovieDetails

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieAccountStates(
        @Path("movie_id") id: Int,
        @Query("session_id") sessionId: String
    ): MovieAccountStates

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") id: Int,
    ): Credits

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") id: Int,
        @Query("ISO-639-1") languageCode: String? = null
    ): ImageCollection

    @GET("movie/latest")
    suspend fun getLatestMovie(): LatestMovie

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendMovies(
        @Path("movie_id") id: Int,
        @Query("page") page: Int = 1
    ): MoviesCollections

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Int,
        @Query("page") page: Int = 1
    ): MoviesCollections

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") id: Int,
    ): VideoContainer

}