package com.bitIO.tvshowcomponent.data.repository

import android.util.Log
import com.bitIO.tvshowcomponent.data.remote.TvShowService
import com.bitIO.tvshowcomponent.data.remote.response.Result
import com.bitIO.tvshowcomponent.data.remote.response.TopRatedTvShowResponse
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import javax.inject.Inject

class TvShowRepositoryImp @Inject constructor(
    private val api: TvShowService
) : TvShowRepository {

    override suspend fun getTopRatedTvShow(): List<Result?>? {
        val result = api.getTopRatedTvShow().body()?.results
        Log.d("TESTTTTTTTTTT33","result = ${result.toString()}\n\n")

        return result
    }


}