package com.example.moviesfeature.uiModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.movies.domain.enities.movie.Movie

data class MovieUiDto(
    val backdropPath: String? = "",
    val genres: String,
    val id: Int,
    val originalLanguage: String,
    val posterPath: String? = "",
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    var isSaved: MutableState<Boolean>
) {
    constructor(
        movie: Movie, isSaved: Boolean
    ) : this(
        backdropPath = movie.backdropPath,
        genres = movie.genres.take(2).joinToString { it.name },
        id = movie.id,
        originalLanguage = movie.originalLanguage,
        posterPath = movie.posterPath,
        releaseDate = movie.releaseDate,
        title = movie.title,
        voteAverage = movie.voteAverage,
        voteCount = movie.voteCount,
        isSaved = mutableStateOf(isSaved)
    )
}