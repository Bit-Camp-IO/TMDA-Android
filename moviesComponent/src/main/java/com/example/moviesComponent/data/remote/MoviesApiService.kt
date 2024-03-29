package com.example.moviesComponent.data.remote

import com.example.moviesComponent.data.dto.account.MovieAccountStatesDto
import com.example.moviesComponent.data.dto.movies.MovieDetailsDto
import com.example.moviesComponent.data.dto.movies.MoviesBriefWrapperDto
import com.example.moviesComponent.data.dto.movies.PersonMovieWrapper
import com.example.sharedComponent.dto.credits.CreditsDto
import com.example.sharedComponent.dto.people.PeoplePageDto
import com.example.sharedComponent.dto.people.PersonDetailsDto
import com.example.sharedComponent.dto.review.ReviewsWrapperDto
import com.example.sharedComponent.dto.videos.VideoContainerDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesApiService {
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



    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") id: Int): ReviewsWrapperDto



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

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int = 1): MoviesBriefWrapperDto

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): MoviesBriefWrapperDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): MoviesBriefWrapperDto

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("page") page: Int = 1): MoviesBriefWrapperDto

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): MoviesBriefWrapperDto


    @POST("account/{account_id}/watchlist")
    suspend fun postMovieToWatchList(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Body body: RequestBody
    )

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") keyword: String,
        @Query("include_adult") includeAdults: Boolean,
        @Query("page") page: Int
    ): MoviesBriefWrapperDto

    @GET("person/{person_id}")
    suspend fun getPersonDetails(@Path("person_id") personId: Int): PersonDetailsDto

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovies(@Path("person_id") personId: Int): PersonMovieWrapper

    @GET("search/person")
    suspend fun searchPeople(
        @Query("query") keyword: String,
        @Query("page") page: Int
    ): PeoplePageDto

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getBookMarkedMovies(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int
    ): MoviesBriefWrapperDto
}