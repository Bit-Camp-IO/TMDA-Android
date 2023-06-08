package com.example.tmda.presentation.movies.moviesList

import com.example.movies.domain.enities.Genre
import com.example.movies.domain.enities.movie.Movie

data class MovieUiDto(
    val isAdult: Boolean,
    val backdropPath: String? = "",
    val genres: List<Genre> = listOf(),
    val id: Int,
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
    var isSaved: Boolean
) {
    constructor(
        movie: Movie, isSaved: Boolean
    ) : this(
        movie.isAdult,
        movie.backdropPath,
        movie.genres,
        movie.id,
        movie.originalLanguage,
        movie.originalTitle,
        movie.overview,
        movie.popularity,
        movie.posterPath,
        movie.releaseDate,
        movie.title,
        movie.hasVideo,
        movie.voteAverage,
        movie.voteCount,
        isSaved
    )
}