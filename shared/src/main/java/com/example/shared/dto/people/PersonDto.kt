package com.example.shared.dto.people

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonDto(
    val adult: Boolean,
    val id: Int,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?
)
