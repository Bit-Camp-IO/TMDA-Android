package com.example.movies.domain.enities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    @Json(name = "english_name") val englishName: String,
    @Json(name = "spoken_languages") val languageCode: String,
    val name: String
)
