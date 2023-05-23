package com.example.movies.data.dto.videos

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class VideoDto(
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val id: String
)
