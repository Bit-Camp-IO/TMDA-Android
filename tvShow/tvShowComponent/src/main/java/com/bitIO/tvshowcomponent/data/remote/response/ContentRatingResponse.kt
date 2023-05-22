package com.bitIO.tvshowcomponent.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentRatingResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("results")
    val results: List<ContentRatingResult>?
)