package com.example.movies.domain.enities

import androidx.room.Entity

@Entity
data class Movie (
    val isAdult: Boolean,
    val backdropPath: String?,
    val genres: List<Genre>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val hasVideo: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
)
