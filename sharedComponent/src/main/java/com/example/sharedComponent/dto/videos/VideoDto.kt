package com.example.sharedComponent.dto.videos

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class VideoDto(
    val key: String,
    val site: String,
    val type:String
)
