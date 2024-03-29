package com.example.moviesComponent.domain.enities.movie

data class MoviesPage(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)