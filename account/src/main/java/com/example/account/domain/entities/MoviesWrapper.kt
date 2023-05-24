package com.example.account.domain.entities


data class MoviesWrapper(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)