package com.bitIO.tvshowcomponent.domain.repository

import androidx.paging.PagingData
import com.bitIO.tvshowcomponent.data.remote.response.CreditsResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDetailsResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDto
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

     fun getPagingTvShows(type: Int): Flow<PagingData<TvShowDto>>
     suspend fun getNowPlayingHomeTvShows() : Flow<List<TvShowDto>>
     suspend fun getTopRatedHomeTvShows() : Flow<List<TvShowDto>>
     suspend fun getPopularHomeTvShows(): Flow<List<TvShowDto>>

     suspend fun getOnTheAirHomeTvShows(): Flow<List<TvShowDto>>
     suspend fun getSimilarTvShows(tvShowId: Int): Flow<List<TvShowDto>>
     suspend fun getTvShowDetails(tvShowId: Int) : Flow<TvShowDetailsResponse>

     suspend fun getCredits(tvShowId: Int) : Flow<CreditsResponse>
}
