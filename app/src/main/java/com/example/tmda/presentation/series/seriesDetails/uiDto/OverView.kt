package com.example.tmda.presentation.series.seriesDetails.uiDto

import androidx.compose.runtime.MutableState
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.shared.entities.Video
import com.example.shared.entities.credits.CastMember
import com.example.shared.entities.review.Review

data class OverView(
    val id: Int,
    val title: String,
    val image: String,
    val overView: String,
    val videos: List<Video>,
    val rating: Double,
    val voteCount: Int,
    val cast: List<CastMember>,
    val similarSeries: List<TvShow>,
    val recommendedSeries: List<TvShow>,
    val releaseYear: String,
    val country: String,
    val company: String,
    val seasonCount: Int,
    val episodesCount: Int,
    val genres: String,
    val savedState: MutableState<Boolean>,
    val reviews: List<Review>
)