package com.example.movies.data.dto.credits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrewMemberDto(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    @Json(name = "known_for_department") val role: String,
    val name: String,
    @Json(name = "original_name") val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "credit_id") val creditId: String,
    val department: String,
    val job: String
)
