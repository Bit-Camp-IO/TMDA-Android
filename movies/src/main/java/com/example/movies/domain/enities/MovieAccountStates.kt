package com.example.movies.domain.enities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieAccountStates(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    val watchlist: Boolean
)