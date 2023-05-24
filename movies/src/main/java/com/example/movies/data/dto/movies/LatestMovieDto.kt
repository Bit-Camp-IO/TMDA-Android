package com.example.movies.data.dto.movies

import com.example.movies.data.dto.shared.GenreDto
import com.example.movies.data.dto.shared.MovieCollectionDetailsDto
import com.example.movies.data.dto.shared.ProductionCompanyDto
import com.example.movies.data.dto.shared.ProductionCountryDto
import com.example.movies.data.dto.shared.SpokenLanguageDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LatestMovieDto(
    @Json(name = "adult") val isAdult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "id") val id: Int,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "video") val hasVideo: Boolean,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    val genres: List<GenreDto>,
    val overview: String,
    val popularity: Double,
    val title: String,
    @Json(name = "belongs_to_collection") val movieCollectionDetails: MovieCollectionDetailsDto?,
    @Json(name = "homepage") val homePage: String,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompanyDto>,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountryDto>,
    @Json(name = "runtime") val runTime: Int,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguageDto>,
    @Json(name = "tagline") val tagLine: String,
    val budget: Int,
    val revenue: Int,
    val status: String,
)
