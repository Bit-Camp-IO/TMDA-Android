package com.bitIO.tvshowcomponent.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowReviews(
    @SerialName("id")
    val id: Int?,
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<ReviewDto>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)