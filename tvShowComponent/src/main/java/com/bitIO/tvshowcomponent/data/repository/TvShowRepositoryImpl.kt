package com.bitIO.tvshowcomponent.data.repository


import com.bitIO.tvshowcomponent.data.mapper.toTvShowDetails
import com.bitIO.tvshowcomponent.data.mapper.toTvShowPage
import com.bitIO.tvshowcomponent.data.remote.TvShowApiService
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.shared.entities.Video
import com.example.shared.entities.credits.Credits
import com.example.shared.mappers.toCredits
import com.example.shared.mappers.toVideo
import javax.inject.Inject


class TvShowRepositoryImpl @Inject constructor(private val api: TvShowApiService) :
    TvShowRepository {


    override suspend fun getPopularTvShows(pageIndex: Int): TvShowPage {
        return api.getPopularTvShows(pageIndex).toTvShowPage()
    }

    override suspend fun getOnTheAirTvShows(pageIndex: Int): TvShowPage {
        return api.getOnTheAirTvShows(pageIndex).toTvShowPage()
    }

    override suspend fun getTopRatedTvShows(pageIndex: Int): TvShowPage {
        return api.getTopRatedTvShows(pageIndex).toTvShowPage()
    }

    override suspend fun getTrendingTvShows(): TvShowPage {
        return api.getTrendyTvShows().toTvShowPage()
    }

    override suspend fun getSimilarTvShows(tvShowId: Int, pageIndex: Int): TvShowPage {
        return api.getSimilarTvShows(tvShowId, pageIndex).toTvShowPage()
    }

    override suspend fun getSeriesVideos(tvShowId: Int): List<Video> {
        return api.getTvVideos(tvShowId).results.map { it.toVideo() }
    }

    override suspend fun searchSeries(keyword: String, includeAdults: Boolean, page: Int): TvShowPage {
        return api.searchSeries(keyword,includeAdults,page).toTvShowPage()
    }


    override suspend fun getTvShowDetails(tvShowId: Int): TvShowDetails {
        return api.getTvShowDetails(tvShowId).toTvShowDetails()
    }

    override suspend fun getCredits(tvShowId: Int): Credits =
        api.getCredits(tvShowId).toCredits()

    override suspend fun getAiringTodayTvShows(pageIndex: Int): TvShowPage {
        return api.getAiringTodayTvShows().toTvShowPage()
    }


}