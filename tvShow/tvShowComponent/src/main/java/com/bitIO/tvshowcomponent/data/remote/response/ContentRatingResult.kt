package com.bitIO.tvshowcomponent.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
data class ContentRatingResult(
    @Json(name = "descriptors")
    val descriptors: List<String?>?,
    @Json(name = "iso_3166_1")
    val iso31661: String?,
    @Json(name = "rating")
    val rating: String?
)