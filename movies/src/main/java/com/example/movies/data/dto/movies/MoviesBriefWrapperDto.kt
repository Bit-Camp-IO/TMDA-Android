package com.example.movies.data.dto.movies
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesBriefWrapperDto(
    val page: Int,
    val results: List<MovieBriefDto>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int

)
