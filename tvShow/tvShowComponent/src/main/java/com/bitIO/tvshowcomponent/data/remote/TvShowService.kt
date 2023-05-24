package com.bitIO.tvshowcomponent.data.remote

import com.bitIO.tvshowcomponent.data.remote.response.ContentRatingResponse
import com.bitIO.tvshowcomponent.data.remote.response.BaseTvShowResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDetailsResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowReviews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TvShowService {

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShow(): Response<BaseTvShowResponse>

    @GET("tv/latest")
    suspend fun getLatestTvShow(): Response<TvShowDetailsResponse>


    @GET("tv/popular")
    suspend fun getPopularTvShow(): Response<BaseTvShowResponse>

    @GET("tv/changes")
    suspend fun getChangesTvShow(): Response<BaseTvShowResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShow(): Response<BaseTvShowResponse>

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShow(): Response<BaseTvShowResponse>

    @GET("tv/{id}")
    suspend fun getTvShowDetails(@Path("id") tvShowId: Int): Response<TvShowDetailsResponse>

    @GET("tv/{id}/similar")
    suspend fun getSimilarTvShow(@Path("id") tvShowId: Int): Response<BaseTvShowResponse>


    @GET("tv/{id}/content_ratings")
    suspend fun getContentRatings(@Path("id") tvShowId: Int): Response<ContentRatingResponse>

    @GET("/tv/{id}/recommendations")
    suspend fun getRecommendations(@Path("id") tvShowId: Int): Response<TvShowDetailsResponse>


    @GET("tv/{id}/reviews")
    suspend fun getReviews(@Path("id") tvShowId: Int): Response<TvShowReviews>
    
}