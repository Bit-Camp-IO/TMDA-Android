package com.example.movies.domain.enities.movies

import com.example.movies.domain.enities.core.Genre
import com.example.movies.domain.enities.core.abstractions.CoreMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieBrief(
    @Json(name = "adult") override val isAdult: Boolean,
    @Json(name = "backdrop_path") override val backdropPath: String?,
    @Json(name = "id") override val id: Int,
    @Json(name = "original_language") override val originalLanguage: String,
    @Json(name = "original_title") override val originalTitle: String,
    @Json(name = "release_date") override val releaseDate: String,
    @Json(name = "poster_path") override val posterPath: String?,
    @Json(name = "video") override val hasVideo: Boolean,
    @Json(name = "vote_average") override val voteAverage: Double,
    @Json(name = "vote_count") override val voteCount: Int,
    @Json(name = "genre_ids") override val genres: List<Genre>,
    override val overview: String,
    override val popularity: Double,
    override val title: String,
    @Json(name = "media_type") val mediaType: String?
): CoreMovie
