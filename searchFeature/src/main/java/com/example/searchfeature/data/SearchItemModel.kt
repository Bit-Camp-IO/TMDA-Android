package com.example.searchfeature.data

data class SearchItemModel(
    val backdropPath: String? = "",
    val genres: String,
    val id: Int,
    val originalLanguage: String,
    val posterPath: String? = "",
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
)
