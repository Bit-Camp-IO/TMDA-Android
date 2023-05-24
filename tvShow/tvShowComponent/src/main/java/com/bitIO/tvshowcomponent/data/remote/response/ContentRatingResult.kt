package com.bitIO.tvshowcomponent.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentRatingResult(
    @SerialName("descriptors")
    val descriptors: List<String?>?,
    @SerialName("iso_3166_1")
    val iso31661: String?,
    @SerialName("rating")
    val rating: String?
)