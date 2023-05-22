package com.example.movies.domain.enities.videos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoContainer(
    val id:Int,
    val results:List<Video>
)
