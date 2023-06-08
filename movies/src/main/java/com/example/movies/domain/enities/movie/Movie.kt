package com.example.movies.domain.enities.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.domain.enities.Genre

@Entity(tableName = "movies")
data class Movie(
    val isAdult: Boolean,
    val backdropPath: String? = "",
    val genres: List<Genre> = listOf(),
    @PrimaryKey val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String? = "",
    val releaseDate: String,
    val title: String,
    val hasVideo: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
)
