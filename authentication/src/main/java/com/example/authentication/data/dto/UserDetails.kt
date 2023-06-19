package com.example.authentication.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass




@JsonClass(generateAdapter = true)
data class UserDetailsDto(
    val avatar: AvatarDto,
    val id: Int,
    val name: String,
    val username: String
)

@JsonClass(generateAdapter = true)
data class AvatarDto(
    val gravatar: GravatarDto,
    val tmdb: TmdbDto
)
@JsonClass(generateAdapter = true)
data class GravatarDto(
    val hash: String
)
@JsonClass(generateAdapter = true)
data class TmdbDto(
    @Json(name="avatar_path")
    val avatarPath: String
)
