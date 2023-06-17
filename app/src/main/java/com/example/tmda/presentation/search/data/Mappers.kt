package com.example.tmda.presentation.search.data

import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.movies.domain.enities.movie.Movie

fun Movie.toSearchItem() =
    SearchItemModel(
        backdropPath,
        genres.take(2).joinToString { it.name },
        id,
        originalLanguage,
        posterPath,
        releaseDate,
        title,
        voteAverage,
        voteCount,
    )


fun TvShow.toSearchItem() =
    SearchItemModel(
        backdropPath,
        genres.take(2).joinToString { it.name },
        id,
        originalLanguage,
        posterPath,
        releaseDate,
        name,
        voteAverage,
        voteCount,
    )
