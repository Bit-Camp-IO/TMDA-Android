package com.example.shared.dto.people

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonDto(
    val adult: Boolean,
    val biography: String,
    val birthday: String,
    @Json(name = "deathday")
    val deathDay: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    @Json(name = "place_of_birth")
    val placeOfBirth: String,
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?
)
