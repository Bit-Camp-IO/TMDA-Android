package com.example.movies.data.remote

import com.example.movies.data.dto.account.MovieAccountStatesDto
import com.example.movies.data.dto.credits.CreditsDto
import com.example.movies.data.dto.image.ImageCollectionDto
import com.example.movies.data.dto.movies.LatestMovieDto
import com.example.movies.data.dto.movies.MovieDetailsDto
import com.example.movies.data.dto.movies.MoviesBriefWrapperDto
import com.example.movies.data.dto.shared.GenreDto
import com.example.movies.data.dto.videos.VideoContainerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): MovieDetailsDto

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieAccountStates(
        @Path("movie_id") id: Int,
        @Query("session_id") sessionId: String
    ): MovieAccountStatesDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") id: Int,
    ): CreditsDto

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") id: Int,
        @Query("ISO-639-1") languageCode: String? = null
    ): ImageCollectionDto

    @GET("movie/latest")
    suspend fun getLatestMovie(): LatestMovieDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendMovies(
        @Path("movie_id") id: Int,
        @Query("page") page: Int = 1
    ): MoviesBriefWrapperDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Int,
        @Query("page") page: Int = 1
    ): MoviesBriefWrapperDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") id: Int,
    ): VideoContainerDto

    @GET("genre/movie/list")
    suspend fun getAllMovieGenres(): List<GenreDto>
}