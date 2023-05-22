package com.bitIO.tvshowcomponent.data.remote

import com.bitIO.tvshowcomponent.data.remote.response.AiringTodayResponse
import com.bitIO.tvshowcomponent.data.remote.response.ChangesTvResponse
import com.bitIO.tvshowcomponent.data.remote.response.ContentRatingResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowResponse
import com.bitIO.tvshowcomponent.data.remote.response.OnTheAirResponse
import com.bitIO.tvshowcomponent.data.remote.response.PopularTvShowResponse
import com.bitIO.tvshowcomponent.data.remote.response.TopRatedTvShowResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TvShowService {

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShow(): Response<TopRatedTvShowResponse>

    @GET("tv/latest")
    suspend fun getLatestTvShow(): Response<TvShowResponse>


    @GET("tv/popular")
    suspend fun getPopularTvShow(): Response<PopularTvShowResponse>

    @GET("tv/changes")
    suspend fun getChangesTvShow(): Response<ChangesTvResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShow(): Response<OnTheAirResponse>

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShow(): Response<AiringTodayResponse>

    @GET("tv/{id}")
    suspend fun getTvShowDetails(@Path("id") tvShowId: Int): Response<TvShowDetailsResponse>

    @GET("tv/{id}/similar")
    suspend fun getSimilarTvShow(@Path("id") tvShowId: Int): Response<TopRatedTvShowResponse>


    @GET("tv/{id}/content_ratings")
    suspend fun getContentRatings(@Path("id") tvShowId: Int): Response<ContentRatingResponse>

    @GET
    suspend fun getRecommendations():Response<TvShowResponse>


}