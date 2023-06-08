package com.example.tmda.presentation.movies.movieDetails

import com.example.movies.domain.enities.Genre
import com.example.movies.domain.enities.MovieCollectionDetails
import com.example.movies.domain.enities.Video
import com.example.movies.domain.enities.credits.Credits
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.enities.movie.MovieDetails
import com.example.movies.domain.enities.review.Review

data class MovieDetailsScreenDto(
    val isAdult: Boolean,
    val backdropPath: String,
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
    val credits: Credits,
    val similarMovies: List<Movie>,
    val recommendedMovies: List<Movie>,
    val reviews: List<Review>,
    val videos: List<Video>,
    val isSaved: Boolean
) {
    constructor(
        movieDetails: MovieDetails,
        credits: Credits,
        similarMovies: List<Movie>,
        recommendedMovies: List<Movie>,
        reviews: List<Review>,
        videos: List<Video>,
        isSaved: Boolean
    ) : this(
        isAdult = movieDetails.isAdult,
        backdropPath = movieDetails.backdropPath,
        movieCollectionDetails = movieDetails.movieCollectionDetails,
        originalLanguage = movieDetails.originalLanguage,
        originalTitle = movieDetails.originalTitle,
        posterPath = movieDetails.posterPath,
        releaseDate = movieDetails.releaseDate,
        voteAverage = movieDetails.voteAverage,
        voteCount = movieDetails.voteCount,
        hasVideo = movieDetails.hasVideo,
        overview = movieDetails.overview,
        popularity = movieDetails.popularity,
        productionCountry = movieDetails.productionCountry,
        title = movieDetails.title,
        genres = movieDetails.genres,
        id = movieDetails.id,
        status = movieDetails.status,
        tagline = movieDetails.tagline,
        homepage = movieDetails.homepage,
        runtime = movieDetails.runtime,
        credits = credits,
        similarMovies = similarMovies,
        recommendedMovies = recommendedMovies,
        reviews = reviews,
        videos = videos,
        isSaved = isSaved
    )
}
