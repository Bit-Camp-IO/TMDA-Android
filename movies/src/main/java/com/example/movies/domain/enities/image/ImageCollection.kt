package com.example.movies.domain.enities.image

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageCollection(
    val id: Int,
    @Json(name = "backdrops") val backDrops: List<ImageItem>,
    @Json(name = "logos") val logos: List<ImageItem>,
    @Json(name = "posters") val posters: List<ImageItem>,
)
