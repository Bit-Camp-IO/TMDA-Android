package com.example.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkTVWrapper(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<NetworkTV>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)