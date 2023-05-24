package com.example.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkCollection(
    @Json(name = "description")
    val description: String,
    @Json(name = "favorite_count")
    val favoriteCount: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "item_count")
    val itemCount: Int,
    @Json(name = "iso_639_1")
    val iso6391: String,
    @Json(name = "list_type")
    val listType: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "poster_path")
    val posterPath: String?
)
