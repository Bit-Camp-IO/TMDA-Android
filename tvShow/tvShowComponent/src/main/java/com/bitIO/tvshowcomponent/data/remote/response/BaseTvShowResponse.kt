package com.bitIO.tvshowcomponent.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
@JsonClass(generateAdapter = true)
data class BaseTvShowResponse(
    @Json(name = "page")
    val page: Int?,
    @Json(name = "results")
    val results: List<TvShowDto>?,
    @Json(name = "total_pages")
    val totalPages: Int?,
    @Json(name = "total_results")
    val totalResults: Int?
)