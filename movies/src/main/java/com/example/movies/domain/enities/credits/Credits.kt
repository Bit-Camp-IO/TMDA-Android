package com.example.movies.domain.enities.credits

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Credits(
    val id: Int,
    val cast: List<CastMember>,
    val crew: List<CrewMember>
)

