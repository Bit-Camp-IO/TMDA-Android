package com.bitIO.tvshowcomponent.domain.repository

import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.example.sharedComponent.entities.Video
import com.example.sharedComponent.entities.credits.Credits
import com.example.sharedComponent.entities.people.PersonDetails
import com.example.sharedComponent.entities.review.Review


interface TvShowRepository {


    suspend fun getTvShowDetails(tvShowId: Int): TvShowDetails
    suspend fun getCredits(tvShowId: Int): Credits

    suspend fun getAiringTodayTvShows(pageIndex: Int): TvShowPage
    suspend fun getTrendingTvShows(): TvShowPage
    suspend fun getPopularTvShows(pageIndex: Int): TvShowPage
    suspend fun getOnTheAirTvShows(pageIndex: Int): TvShowPage
    suspend fun getTopRatedTvShows(pageIndex: Int): TvShowPage
    suspend fun getSimilarTvShows(tvShowId: Int, pageIndex: Int): TvShowPage
    suspend fun getSeriesVideos(tvShowId: Int): List<Video>
    suspend fun searchSeries(keyword: String, includeAdults: Boolean, page: Int): TvShowPage
    suspend fun getRecommendedTvShows(tvShowId: Int, pageIndex: Int): TvShowPage

    suspend fun getPersonDetails(personId: Int): PersonDetails
    suspend fun getPersonSeries(personId: Int): List<TvShow>
    suspend fun getBookMarkedSeries(
        accountId: Int,
        sessionId: String,
        page: Int
    ): TvShowPage

    suspend fun getTvShowReviews(tvShowId: Int):List<Review>
    suspend fun getTvSavedState(seriesId: Int, sessionId: String): Boolean
    suspend fun addSeriesToWatchList(sessionId: String, seriesId: Int, isAddRequest: Boolean)
}
