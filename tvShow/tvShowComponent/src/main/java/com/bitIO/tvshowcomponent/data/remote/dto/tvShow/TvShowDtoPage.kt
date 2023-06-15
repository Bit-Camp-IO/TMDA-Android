package com.bitIO.tvshowcomponent.data.remote.dto.tvShow


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class TvShowDtoPage(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<TvShowDto>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)