package com.example.sharedComponent.dto.videos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoContainerDto(
    val results:List<VideoDto>
)
