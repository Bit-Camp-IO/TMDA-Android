package com.bitIO.tvshowcomponent.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ContentRatingResponse(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "results")
    val results: List<ContentRatingResult>?
)