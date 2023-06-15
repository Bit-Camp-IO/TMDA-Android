package com.example.shared.dto.details

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDto(val id: Int, val name: String)