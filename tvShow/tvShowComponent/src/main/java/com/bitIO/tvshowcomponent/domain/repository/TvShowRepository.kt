package com.bitIO.tvshowcomponent.domain.repository

import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.example.shared.entities.Video
import com.example.shared.entities.credits.Credits


interface TvShowRepository {



    suspend fun getTvShowDetails(tvShowId: Int): TvShowDetails
    suspend fun getCredits(tvShowId: Int): Credits

    suspend fun getAiringTodayTvShows(pageIndex: Int): TvShowPage
    suspend fun getTrendingTvShows(): TvShowPage
    suspend fun getPopularTvShows(pageIndex: Int): TvShowPage
    suspend fun getOnTheAirTvShows(pageIndex: Int): TvShowPage
    suspend fun getTopRatedTvShows(pageIndex: Int): TvShowPage
    suspend fun getSimilarTvShows(tvShowId: Int, pageIndex: Int): TvShowPage
    suspend fun getSeriesVideos(tvShowId: Int):List<Video>


}
