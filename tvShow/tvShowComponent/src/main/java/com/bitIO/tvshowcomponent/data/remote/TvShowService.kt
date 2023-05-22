package com.bitIO.tvshowcomponent.data.remote

import com.bitIO.tvshowcomponent.data.remote.response.TopRatedTvShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowService {

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShow(): Response<TopRatedTvShowResponse>

}