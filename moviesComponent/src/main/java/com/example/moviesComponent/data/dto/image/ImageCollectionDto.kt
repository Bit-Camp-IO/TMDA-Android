package com.example.moviesComponent.data.dto.image

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageCollectionDto(
    val id: Int,
    @Json(name = "backdrops") val backDrops: List<ImageItemDto>,
    @Json(name = "logos") val logos: List<ImageItemDto>,
    @Json(name = "posters") val posters: List<ImageItemDto>,
)
