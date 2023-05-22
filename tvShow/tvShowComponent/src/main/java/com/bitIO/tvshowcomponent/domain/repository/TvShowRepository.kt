package com.bitIO.tvshowcomponent.domain.repository

import com.bitIO.tvshowcomponent.data.remote.response.Result

interface TvShowRepository {

    suspend fun getTopRatedTvShow(): List<Result?>?

}