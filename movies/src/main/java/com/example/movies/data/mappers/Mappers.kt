package com.example.movies.data.mappers

import com.example.movies.data.dto.movies.MovieBriefDto
import com.example.movies.data.dto.movies.MovieDetailsDto
import com.example.movies.data.dto.movies.MoviesBriefWrapperDto
import com.example.movies.data.dto.shared.GenreDto
import com.example.movies.data.dto.shared.MovieCollectionDetailsDto
import com.example.movies.data.util.genreMap
import com.example.movies.domain.enities.Genre
import com.example.movies.domain.enities.Movie
import com.example.movies.domain.enities.MovieCollectionDetails
import com.example.movies.domain.enities.MovieDetails
import com.example.movies.domain.enities.MoviesPage

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        isAdult = isAdult,
        backdropPath = backdropPath,
        movieCollectionDetails = movieCollectionDetailsDto.toMovieCollectionDetails(),
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        hasVideo = hasVideo,
        overview = overview,
        popularity = popularity,
        title = title,
        genres = genres.map { it.toGenre() },
        id = id,
        status = status,
        tagline = tagline,
        homepage = homepage,
        runtime = runtime,
    )
}

fun MovieCollectionDetailsDto.toMovieCollectionDetails(): MovieCollectionDetails {
    return MovieCollectionDetails(
        id = id,
        name = name,
        posterPath = posterPath,
        backdropPath = backdropPath
    )
}

fun GenreDto.toGenre() = Genre(id = id, name = name)


fun MovieBriefDto.toMovie(): Movie {
    return Movie(
        isAdult = isAdult,
        backdropPath = backdropPath,
        genres = genreIds.map { it.toGenre() },
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        hasVideo = hasVideo,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}

fun MoviesBriefWrapperDto.toMoviePage() = MoviesPage(
    page = page,
    results = results.map { it.toMovie() },
    totalPages = totalPages,
    totalResults = totalResults

)

internal fun Int.toGenre(): Genre {
    return Genre(this, genreMap[this]!!)
}
