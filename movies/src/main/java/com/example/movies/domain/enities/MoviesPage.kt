package com.example.movies.domain.enities

data class MoviesPage(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)