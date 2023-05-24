package com.example.movies.data.dto.shared

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCountryDto(
    @Json(name = "iso_3166_1") val countryCode: String,
    val name: String
)