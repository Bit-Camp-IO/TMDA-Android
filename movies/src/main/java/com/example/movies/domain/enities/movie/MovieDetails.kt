package com.example.movies.domain.enities.movie

import com.example.movies.domain.enities.Genre
import com.example.movies.domain.enities.MovieCollectionDetails


data class MovieDetails(
    val isAdult: Boolean,
    val backdropPath: String?,
    val movieCollectionDetails: MovieCollectionDetails?,
    val originalLanguage: String,
    val originalTitle: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val hasVideo: Boolean,
    val overview: String,
    val popularity: Double,
    val productionCountry: String,
    val title: String,
    val genres: List<Genre>,
    val id: Int,
    val status: String,
    val tagline: String,
    val homepage: String,
    val runtime: Int,
)
