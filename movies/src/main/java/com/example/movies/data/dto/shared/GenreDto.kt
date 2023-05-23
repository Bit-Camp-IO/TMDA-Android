package com.example.movies.data.dto.shared

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDto(val id: Int, val name: String)