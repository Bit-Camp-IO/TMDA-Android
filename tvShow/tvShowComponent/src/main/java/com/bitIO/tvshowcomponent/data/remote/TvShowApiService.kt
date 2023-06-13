package com.bitIO.tvshowcomponent.data.remote

import com.bitIO.tvshowcomponent.data.remote.response.BaseTvShowResponse
import com.bitIO.tvshowcomponent.data.remote.response.ContentRatingResponse
import com.bitIO.tvshowcomponent.data.remote.response.CreditsResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDetailsResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowReviews
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApiService {

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(@Query("page") page: Int?): BaseTvShowResponse


    @GET("tv/latest")
    suspend fun getLatestTvShow(): TvShowDetailsResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("page") page: Int?): BaseTvShowResponse

    @GET("tv/changes")
    suspend fun getChangesTvShows(): BaseTvShowResponse

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(@Query("page") page: Int?): BaseTvShowResponse

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(): BaseTvShowResponse

    @GET("tv/{id}")
    suspend fun getTvShowDetails(@Path("id") tvShowId: Int): TvShowDetailsResponse

    @GET("tv/{id}/similar")
    suspend fun getSimilarTvShows(@Path("id") tvShowId: Int): BaseTvShowResponse


    @GET("tv/{id}/content_ratings")
    suspend fun getContentRatings(@Path("id") tvShowId: Int): ContentRatingResponse

    @GET("/tv/{id}/recommendations")
    suspend fun getRecommendations(@Path("id") tvShowId: Int): TvShowDetailsResponse


    @GET("tv/{id}/reviews")
    suspend fun getReviews(@Path("id") tvShowId: Int): TvShowReviews

    @GET("tv/{id}/credits")
    suspend fun getCredits(@Path("id") tvShowId: Int): CreditsResponse

}