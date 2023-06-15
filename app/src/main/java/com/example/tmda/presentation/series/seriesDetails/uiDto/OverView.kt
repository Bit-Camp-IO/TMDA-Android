package com.example.tmda.presentation.series.seriesDetails.uiDto

import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.shared.entities.Video
import com.example.shared.entities.credits.CastMember

data class OverView(
    val id: Int,
    val title: String,
    val image: String,
    val overView: String,
    val videos: List<Video>,
    val rating: Double,
    val voteCount:Int,
    val cast: List<CastMember>,
    val similarSeries: List<TvShow>,
    val releaseYear: String,
    val country: String,
    val company: String,
    val seasonCount: Int,
    val episodesCount: Int,
    val genres:String
    )