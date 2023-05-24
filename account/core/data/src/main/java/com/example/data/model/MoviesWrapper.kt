package com.example.data.model


data class MoviesWrapper(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)