package com.bitIO.tvshowcomponent.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bitIO.tvshowcomponent.data.paging.TvShowsPagingSource
import com.bitIO.tvshowcomponent.data.remote.TvShowApiService
import com.bitIO.tvshowcomponent.data.remote.response.CreditsResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDetailsResponse
import com.bitIO.tvshowcomponent.data.remote.response.TvShowDto
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private const val PAGE_SIZE = 20

class TvShowRepositoryImp @Inject constructor(private val api: TvShowApiService) : TvShowRepository {
    override fun getPagingTvShows(type: Int): Flow<PagingData<TvShowDto>> {
        return Pager(config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            return@Pager when (type) {
                0 -> TvShowsPagingSource(api::getPopularTvShows)
                1 -> TvShowsPagingSource(api::getOnTheAirTvShows)
                2 -> TvShowsPagingSource(api::getTopRatedTvShows)
                else -> TvShowsPagingSource(api::getPopularTvShows)
            }
        }).flow
    }

    override suspend fun getNowPlayingHomeTvShows(): Flow<List<TvShowDto>> =
        flowOf(api.getAiringTodayTvShows().results?.shuffled()?.take(5)!!)

    override suspend fun getTopRatedHomeTvShows(): Flow<List<TvShowDto>> =
        flowOf(api.getTopRatedTvShows(null).results?.shuffled()?.take(5)!!)


    override suspend fun getOnTheAirHomeTvShows(): Flow<List<TvShowDto>> =
        flowOf(api.getOnTheAirTvShows(null).results?.shuffled()?.take(5)!!)

    override suspend fun getPopularHomeTvShows(): Flow<List<TvShowDto>> =
        flowOf(api.getPopularTvShows(null).results?.shuffled()?.take(5)!!)

    override suspend fun getTvShowDetails(tvShowId: Int): Flow<TvShowDetailsResponse> =
        flowOf(api.getTvShowDetails(tvShowId))

    override suspend fun getCredits(tvShowId: Int): Flow<CreditsResponse> =
        flowOf(api.getCredits(tvShowId))
}