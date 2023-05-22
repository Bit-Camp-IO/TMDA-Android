package com.bitIO.tvshowcomponent.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowResponse(
    @SerialName("adult")
    val adult: Boolean?,
    @SerialName("backdrop_path")
    val backdropPath: Any?,
    @SerialName("created_by")
    val createdBy: List<Any?>?,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Any?>?,
    @SerialName("first_air_date")
    val firstAirDate: String?,
    @SerialName("genres")
    val genres: List<Any?>?,
    @SerialName("homepage")
    val homepage: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("in_production")
    val inProduction: Boolean?,
    @SerialName("languages")
    val languages: List<Any?>?,
    @SerialName("last_air_date")
    val lastAirDate: String?,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir?,
    @SerialName("name")
    val name: String?,
    @SerialName("networks")
    val networks: List<Any?>?,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: NextEpisodeToAir?,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int?,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int?,
    @SerialName("origin_country")
    val originCountry: List<String?>?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_name")
    val originalName: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("poster_path")
    val posterPath: Any?,
    @SerialName("production_companies")
    val productionCompanies: List<Any?>?,
    @SerialName("production_countries")
    val productionCountries: List<Any?>?,
    @SerialName("seasons")
    val seasons: List<Season?>?,
    @SerialName("spoken_languages")
    val spokenLanguages: List<Any?>?,
    @SerialName("status")
    val status: String?,
    @SerialName("tagline")
    val tagline: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
)