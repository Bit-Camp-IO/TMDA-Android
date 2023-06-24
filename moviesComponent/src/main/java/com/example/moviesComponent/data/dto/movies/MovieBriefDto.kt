package com.example.moviesComponent.data.dto.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieBriefDto(
    @Json(name = "adult") val isAdult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "id") val id: Int,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "video") val hasVideo: Boolean,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    val overview: String,
    val popularity: Double,
    val title: String,
    @Json(name = "media_type") val mediaType: String?
)
