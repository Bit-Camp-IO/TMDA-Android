package com.example.movies.domain.enities.core

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(val id: Int, val name: String?)
