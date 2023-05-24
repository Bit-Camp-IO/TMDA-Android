package com.example.network.retrofit


import com.example.network.model.NetworkAccountDetails
import com.example.network.model.NetworkCollectionsWrapper
import com.example.network.model.NetworkMoviesWrapper
import com.example.network.model.NetworkPostResponse
import com.example.network.model.NetworkRatedEpisodesWrapper
import com.example.network.model.NetworkTVWrapper
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitAccountApi {
    // TODO : add optional query parameters

    @GET("{account_id}")
    suspend fun getAccountDetails(@Path("account_id") accountId: Int): NetworkAccountDetails

    @GET("{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(@Path("account_id") accountId: Int): NetworkMoviesWrapper

    @GET("{account_id}/favorite/tv")
    suspend fun getFavoriteTV(@Path("account_id") accountId: Int): NetworkTVWrapper

    @GET("{account_id}/lists")
    suspend fun getLists(@Path("account_id") accountId: Int): NetworkCollectionsWrapper

    @GET("{account_id}/rated/movies")
    suspend fun getRatedMovies(@Path("account_id") accountId: Int): NetworkMoviesWrapper

    @GET("{account_id}/rated/tv")
    suspend fun getRatedTV(@Path("account_id") accountId: Int): NetworkTVWrapper

    @GET("{account_id}/rated/tv/episodes")
    suspend fun getRatedTVEpisodes(@Path("account_id") accountId: Int): NetworkRatedEpisodesWrapper

    @GET("{account_id}/watchlist/movies")
    suspend fun getWatchListMovies(@Path("account_id") accountId: Int): NetworkMoviesWrapper

    @GET("{account_id}/watchlist/tv")
    suspend fun getWatchListTV(@Path("account_id") accountId: Int): NetworkTVWrapper

    @FormUrlEncoded
    @POST("{account_id}/favorite")
    suspend fun addToFavorites(
        @Path("account_id") accountId: Int,
        @Field("movie_id") movieId: Int,
        @Field("favorite") favorite: Boolean = true
    ): NetworkPostResponse


    @FormUrlEncoded
    @POST("{account_id}/watchlist")
    suspend fun addToWatchList(
        @Path("account_id") accountId: Int,
        @Field("movie_id") movieId: Int,
        @Field("movie_watchlist") favorite: Boolean = true
    ): NetworkPostResponse
}