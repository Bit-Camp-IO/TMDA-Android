package com.example.tmda.presentation.movies.moviesList

data class UiPage<T : Any>(
    val page: Int,
    val results: List<T>,
    val totalPages: Int,
    val isError: Boolean
)