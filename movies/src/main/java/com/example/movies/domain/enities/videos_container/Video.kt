package com.example.movies.domain.enities.videos_container

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Video(
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val id: String
)
