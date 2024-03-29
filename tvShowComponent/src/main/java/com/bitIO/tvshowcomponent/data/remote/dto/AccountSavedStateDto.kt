package com.bitIO.tvshowcomponent.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountSavedStateDto(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    @Json(name = "watchlist") val watchList: Boolean
)
