package com.bitIO.tvshowcomponent.data.repository

import com.bitIO.tvshowcomponent.data.remote.TvShowApiService
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDto
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository

class TvShowRepositoryImp  constructor(
    private val api: TvShowApiService
) : TvShowRepository {

    override suspend fun getTopRatedTvShow(): List<TvShowDto>? {
        return api.getTopRatedTvShow().body()?.results
    }


}