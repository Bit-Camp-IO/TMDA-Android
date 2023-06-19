package com.example.movies.data.dto.movies

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonMovieWrapper(
    val cast: List<MovieBriefDto>,
    val crew: List<MovieBriefDto>
)
