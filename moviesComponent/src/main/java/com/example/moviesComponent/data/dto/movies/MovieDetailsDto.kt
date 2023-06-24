package com.example.moviesComponent.data.dto.movies

import com.example.moviesComponent.data.dto.shared.MovieCollectionDetailsDto
import com.example.sharedComponent.dto.details.GenreDto
import com.example.sharedComponent.dto.details.ProductionCompanyDto
import com.example.sharedComponent.dto.details.ProductionCountryDto
import com.example.sharedComponent.dto.details.SpokenLanguageDto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsDto(
    @Json(name = "adult") val isAdult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "belongs_to_collection") val movieCollectionDetailsDto: MovieCollectionDetailsDto?,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "video") val hasVideo: Boolean,
    val overview: String,
    val popularity: Double,
    val title: String,
    val genres: List<GenreDto>,
    val id: Int,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompanyDto>,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountryDto>,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguageDto>,
    val status: String,
    val tagline: String,
    val budget: Int,
    val homepage: String,
    val revenue: Long,
    val runtime: Int,
)