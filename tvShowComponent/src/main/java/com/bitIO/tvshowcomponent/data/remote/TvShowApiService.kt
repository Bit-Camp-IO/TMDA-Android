package com.bitIO.tvshowcomponent.data.remote


import com.bitIO.tvshowcomponent.data.remote.dto.AccountSavedStateDto
import com.bitIO.tvshowcomponent.data.remote.dto.tvShow.PersonSeriesWrapper
import com.bitIO.tvshowcomponent.data.remote.dto.tvShow.TvShowDtoPage
import com.bitIO.tvshowcomponent.data.remote.dto.tvShow.details.TvShowDetailsDto
import com.example.sharedComponent.dto.credits.CreditsDto
import com.example.sharedComponent.dto.people.PersonDetailsDto
import com.example.sharedComponent.dto.review.ReviewsWrapperDto
import com.example.sharedComponent.dto.videos.VideoContainerDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApiService {
    //TvShowPage
    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(@Query("page") page: Int): TvShowDtoPage

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("page") page: Int): TvShowDtoPage

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(@Query("page") page: Int): TvShowDtoPage

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(): TvShowDtoPage

    @GET("tv/{series_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("series_id") tvShowId: Int,
        @Query("page") page: Int
    ): TvShowDtoPage

    @GET("tv/{series_id}/recommendations")
    suspend fun getRecommendations(
        @Path("series_id") tvShowId: Int,
        @Query("page") page: Int
    ): TvShowDtoPage


    @GET("trending/tv/{time_window}")
    suspend fun getTrendyTvShows(@Path("time_window") timeWindow: String = "day"): TvShowDtoPage

    //tvShowDetails
    @GET("tv/{series_id}")
    suspend fun getTvShowDetails(@Path("series_id") tvShowId: Int): TvShowDetailsDto




    @GET("tv/{series_id}/reviews")
    suspend fun getTvReviews(
        @Path("series_id") tvShowId: Int,
        @Query("page") page: Int
    ): ReviewsWrapperDto

    @GET("tv/{series_id}/credits")
    suspend fun getCredits(@Path("series_id") tvShowId: Int): CreditsDto

    @GET("tv/{series_id}/videos")
    suspend fun getTvVideos(@Path("series_id") tvShowId: Int): VideoContainerDto

    @GET("search/tv")
    suspend fun searchSeries(
        @Query("query") keyword: String,
        @Query("include_adult") includeAdults: Boolean,
        @Query("page") page: Int
    ): TvShowDtoPage

    @GET("person/{person_id}")
    suspend fun getPersonDetails(@Path("person_id") personId: Int): PersonDetailsDto

    @GET("person/{person_id}/tv_credits")
    suspend fun getPersonSeries(@Path("person_id") personId: Int): PersonSeriesWrapper

    @GET("account/{account_id}/watchlist/tv")
    suspend fun getBookMarkedSeries(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int
    ): TvShowDtoPage

    @POST("account/{account_id}/watchlist")
    suspend fun postToWatchList(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Body body: RequestBody
    )

    @GET("tv/{series_id}/account_states")
    suspend fun getTvSavedState(
        @Path("series_id") seriesId: Int,
        @Query("session_id") sessionId: String
    ): AccountSavedStateDto

}