package com.example.movies.data.dto.videos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoContainerDto(
    val id:Int,
    val results:List<VideoDto>
)
