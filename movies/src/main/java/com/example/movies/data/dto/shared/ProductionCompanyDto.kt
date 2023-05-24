package com.example.movies.data.dto.shared

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCompanyDto(
    val id: Int,
    @Json(name = "logo_path") val logoPath: String,
    val name: String,
    @Json(name = "origin_country") val originCountry: String
)