package com.example.tvshowfeature.seriesDetails.uiDto

import androidx.compose.runtime.mutableStateOf
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.example.shared.entities.Video
import com.example.shared.entities.credits.Credits
import com.example.shared.entities.review.Review

fun makeOverView(
    tvShowDetails: TvShowDetails,
    videos: List<Video>,
    credits: Credits,
    similarSeries: List<TvShow>,
    recommendedSeries: List<TvShow>,
    isSaved: Boolean,
    reviews:List<Review>
): OverView {
    return OverView(
        id = tvShowDetails.id,
        title = tvShowDetails.title,
        image = tvShowDetails.imageURL,
        overView = tvShowDetails.overview,
        videos = videos,
        rating = tvShowDetails.voteAverage,
        voteCount = tvShowDetails.voteCount,
        cast = credits.cast,
        similarSeries = similarSeries,
        recommendedSeries=recommendedSeries,
        releaseYear = tvShowDetails.date.take(4),
        country = tvShowDetails.originCountry,
        company = tvShowDetails.network,
        seasonCount = tvShowDetails.seasonCount,
        episodesCount = tvShowDetails.episodesCount,
        genres = tvShowDetails.genres.joinToString { it.name },
        savedState = mutableStateOf(isSaved),
        reviews
    )
}