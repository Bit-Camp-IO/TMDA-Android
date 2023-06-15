package com.bitIO.tvshowcomponent.data.remote.dto.tvShow.details


import com.bitIO.tvshowcomponent.data.remote.response.CreatedBy
import com.bitIO.tvshowcomponent.data.remote.response.LastEpisodeToAir
import com.bitIO.tvshowcomponent.data.remote.response.Network
import com.bitIO.tvshowcomponent.data.remote.response.NextEpisodeToAir
import com.bitIO.tvshowcomponent.data.remote.response.ProductionCompany
import com.bitIO.tvshowcomponent.data.remote.response.ProductionCountry
import com.bitIO.tvshowcomponent.data.remote.response.SpokenLanguage
import com.example.shared.dto.details.GenreDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowDetailsDto(
   // val adult: Boolean?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "created_by")
    val createdBy: List<CreatedBy>?,
    @Json(name = "episode_run_time")
    val episodeRunTime: List<Int>?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    val genres: List<GenreDto>,
    val homepage: String?,
    val id: Int,
    @Json(name = "in_production")
    val inProduction: Boolean?,
    val languages: List<String>?,
    @Json(name = "last_air_date")
    val lastAirDate: String?,
    @Json(name = "last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir?,
 //   @Json(name = "name")
    val name: String,
   // @Json(name = "networks")
    val networks: List<Network>?,
    @Json(name = "next_episode_to_air")
    val nextEpisodeToAir: NextEpisodeToAir?,
    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Int?,
    @Json(name = "number_of_seasons")
    val numberOfSeasons: Int?,
    @Json(name = "origin_country")
    val originCountry: List<String>?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "original_name")
    val originalName: String?,
 //   @Json(name = "overview")
    val overview: String?,
   // @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>?,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>?,
 //   @Json(name = "seasons")
    val seasons: List<Season>?,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>?,
 //   @Json(name = "status")
    val status: String?,
    @Json(name = "tagline")
    val tagline: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "vote_count")
    val voteCount: Int?
)