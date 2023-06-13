package com.example.tmda.presentation.movies.moviesList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.movies.domain.enities.movie.Movie

data class MovieUiDto(
    // val isAdult: Boolean,
    val backdropPath: String? = "",
    val genres: String,
    val id: Int,
    val originalLanguage: String,
    // val originalTitle: String,
    // val overview: String,
    // val popularity: Double,
    val posterPath: String? = "",
    val releaseDate: String,
    val title: String,
    //  val hasVideo: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    var isSaved: MutableState<Boolean>
) {
    constructor(
        movie: Movie, isSaved: Boolean
    ) : this(
        //   movie.isAdult,
        backdropPath = movie.backdropPath,
        genres = movie.genres.take(2).joinToString { it.name },
        id = movie.id,
        originalLanguage = movie.originalLanguage,
        //   movie.originalTitle,
        //   movie.overview,
        //  popularity = movie.popularity,
        posterPath = movie.posterPath,
        releaseDate = movie.releaseDate,
        title = movie.title,
        //    movie.hasVideo,
        voteAverage = movie.voteAverage,
        voteCount = movie.voteCount,
        isSaved = mutableStateOf(isSaved)
    )
}