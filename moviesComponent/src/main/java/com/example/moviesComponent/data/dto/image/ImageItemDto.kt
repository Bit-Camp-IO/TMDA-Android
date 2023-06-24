package com.example.moviesComponent.data.dto.image

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageItemDto(
    @Json(name = "aspect_ratio") val aspectRatio: Double,
    val height: Int,
    @Json(name = "iso_639_1") val languageCode: String?,
    @Json(name = "file_path") val filePath: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    val width: Int
)
