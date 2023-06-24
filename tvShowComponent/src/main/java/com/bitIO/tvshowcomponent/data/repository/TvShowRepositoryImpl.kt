package com.bitIO.tvshowcomponent.data.repository


import com.bitIO.tvshowcomponent.data.mapper.toTVShow
import com.bitIO.tvshowcomponent.data.mapper.toTvShowDetails
import com.bitIO.tvshowcomponent.data.mapper.toTvShowPage
import com.bitIO.tvshowcomponent.data.remote.TvShowApiService
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.shared.entities.Video
import com.example.shared.entities.credits.Credits
import com.example.shared.entities.people.PersonDetails
import com.example.shared.entities.review.Review
import com.example.shared.mappers.toCredits
import com.example.shared.mappers.toPersonDetails
import com.example.shared.mappers.toReview
import com.example.shared.mappers.toVideo
import okhttp3.MediaType
import okhttp3.RequestBody
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

    override suspend fun getRecommendedTvShows(tvShowId: Int, pageIndex: Int): TvShowPage {
        return api.getRecommendations(tvShowId, pageIndex).toTvShowPage()
    }


    override suspend fun getSeriesVideos(tvShowId: Int): List<Video> {
        return api.getTvVideos(tvShowId).results.map { it.toVideo() }
    }

    override suspend fun searchSeries(
        keyword: String,
        includeAdults: Boolean,
        page: Int
    ): TvShowPage {
        return api.searchSeries(keyword, includeAdults, page).toTvShowPage()
    }


    override suspend fun getTvShowDetails(tvShowId: Int): TvShowDetails {
        return api.getTvShowDetails(tvShowId).toTvShowDetails()
    }

    override suspend fun getCredits(tvShowId: Int): Credits =
        api.getCredits(tvShowId).toCredits()

    override suspend fun getAiringTodayTvShows(pageIndex: Int): TvShowPage {
        return api.getAiringTodayTvShows().toTvShowPage()
    }

    override suspend fun getPersonDetails(personId: Int): PersonDetails {
        return api.getPersonDetails(personId).toPersonDetails()
    }

    override suspend fun getPersonSeries(personId: Int): List<TvShow> {
        return api.getPersonSeries(personId).cast.map { it.toTVShow() }
    }

    override suspend fun getBookMarkedSeries(
        accountId: Int,
        sessionId: String,
        page: Int
    ): TvShowPage {
      return  api.getBookMarkedSeries(accountId, sessionId, page).toTvShowPage()
    }

    override suspend fun getTvShowReviews(tvShowId: Int): List<Review> {
       return api.getTvReviews(tvShowId,1).results.map { it.toReview()}
    }
    override suspend fun getTvSavedState(seriesId: Int,sessionId: String): Boolean {
        return api.getTvSavedState(seriesId, sessionId).watchList
    }
    override suspend fun addSeriesToWatchList(sessionId: String,seriesId: Int, isAddRequest: Boolean) {
        api.postToWatchList(
            accountId = 16874876,
            sessionId =sessionId,
            body = makePostToWatchListBody(type = "tv", mediaId = seriesId, isAddRequest)
        )
    }
    private fun makePostToWatchListBody(
        type: String,
        mediaId: Int,
        isSaveRequest: Boolean
    ): RequestBody {
        val mediaType = MediaType.parse("application/json")
        return RequestBody.create(
            mediaType,
            "{\"media_type\":\"$type\",\"media_id\":\"$mediaId\",\"watchlist\":$isSaveRequest}"
        )
    }
}