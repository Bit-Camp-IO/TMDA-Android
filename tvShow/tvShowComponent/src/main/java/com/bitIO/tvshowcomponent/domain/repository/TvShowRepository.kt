package com.bitIO.tvshowcomponent.domain.repository

import com.bitIO.tvshowcomponent.data.remote.response.TvShowDto

interface TvShowRepository {

    suspend fun getTopRatedTvShow(): List<TvShowDto>?

}