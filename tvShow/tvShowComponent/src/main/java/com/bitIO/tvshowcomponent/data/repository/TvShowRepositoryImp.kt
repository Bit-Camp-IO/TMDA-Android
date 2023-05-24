package com.bitIO.tvshowcomponent.data.repository

import android.util.Log
import com.bitIO.tvshowcomponent.data.remote.TvShowService
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDto
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import retrofit2.Response
import javax.inject.Inject

class TvShowRepositoryImp @Inject constructor(
    private val api: TvShowService
) : TvShowRepository {

    override suspend fun getTopRatedTvShow(): List<TvShowDto>? {
        return api.getTopRatedTvShow().body()?.results
    }


}