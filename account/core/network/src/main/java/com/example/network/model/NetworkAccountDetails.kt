package com.example.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class NetworkAccountDetails(
    @Json(name = "avatar")
    val avatar: Avatar,
    @Json(name = "id")
    val id: Int,
    @Json(name = "iso_639_1")
    val iso6391: String,
    @Json(name = "iso_3166_1")
    val iso31661: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "include_adult")
    val includeAdult: Boolean,
    @Json(name = "username")
    val username: String
) {
    @JsonClass(generateAdapter = true)
    data class Avatar(
        @Json(name = "gravatar")
        val gravatar: Gravatar,
        @Json(name = "tmdb")
        val tmdb: Tmdb
    ) {
        @JsonClass(generateAdapter = true)
        data class Gravatar(
            @Json(name = "hash")
            val hash: String
        )
        @JsonClass(generateAdapter = true)
        data class Tmdb(
            @Json(name = "avatar_path")
            val avatarPath: String?
        )
    }
}

