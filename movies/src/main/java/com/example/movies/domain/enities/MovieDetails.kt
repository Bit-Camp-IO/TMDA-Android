package com.example.movies.domain.enities

import com.example.movies.data.dto.shared.GenreDto

data class MovieDetails(
    val isAdult: Boolean,
    val backdropPath: String,
    val movieCollectionDetails: MovieCollectionDetails,
    val originalLanguage: String,
    val originalTitle: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val hasVideo: Boolean,
    val overview: String,
    val popularity: Double,
    val title: String,
    val genres: List<Genre>,
    val id: Int,
    val status: String,
    val tagline: String,
    val homepage: String,
    val runtime: Int,
)
