package com.example.account.data.dto.collection


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkCollectionsWrapper(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<NetworkCollection>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)