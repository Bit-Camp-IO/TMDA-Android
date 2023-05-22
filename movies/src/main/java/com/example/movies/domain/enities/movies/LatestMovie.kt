package com.example.movies.domain.enities.movies

import com.example.movies.domain.enities.core.Genre
import com.example.movies.domain.enities.core.abstractions.CoreMovie
import com.example.movies.domain.enities.core.ProductionCompany
import com.example.movies.domain.enities.core.ProductionCountry
import com.example.movies.domain.enities.core.SpokenLanguage
import com.example.movies.domain.enities.core.MovieCollectionDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LatestMovie(
    @Json(name = "adult") override val isAdult: Boolean,
    @Json(name = "backdrop_path") override val backdropPath: String?,
    @Json(name = "id") override val id: Int,
    @Json(name = "original_language") override val originalLanguage: String,
    @Json(name = "original_title") override val originalTitle: String,
    @Json(name = "release_date") override val releaseDate: String,
    @Json(name = "poster_path") override val posterPath: String?,
    @Json(name = "video") override val hasVideo: Boolean,
    @Json(name = "vote_average") override val voteAverage: Double,
    @Json(name = "vote_count") override val voteCount: Int,
    override val genres: List<Genre>,
    override val overview: String,
    override val popularity: Double,
    override val title: String,
    @Json(name = "belongs_to_collection") val belongsToMovieCollectionDetails: MovieCollectionDetails?,
    @Json(name = "homepage") val homePage: String,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountry>,
    @Json(name = "runtime") val runTime: Int,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    @Json(name = "tagline") val tagLine: String,
    val budget: Int,
    val revenue: Int,
    val status: String,
): CoreMovie
