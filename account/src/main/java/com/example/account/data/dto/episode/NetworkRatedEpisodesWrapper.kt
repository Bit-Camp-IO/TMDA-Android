package com.example.account.data.dto.episode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkRatedEpisodesWrapper(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<NetworkEpisode>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)