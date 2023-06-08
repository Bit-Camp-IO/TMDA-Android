package com.example.tmda.presentation.movies.moviesList

data class UiPage<T:Any> (
    val page: Int,
    val items: List<T>,
    val totalPages: Int,
    val totalResults: Int
)