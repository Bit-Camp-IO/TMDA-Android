package com.example.moviesComponent.data.dto.account

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieAccountStatesDto(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    @Json(name = "watchlist") val watchList: Boolean
)