package com.bitIO.tvshowcomponent.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LastEpisodeToAir(
    @SerialName("air_date")
    val airDate: String?,
    @SerialName("episode_number")
    val episodeNumber: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("production_code")
    val productionCode: String?,
    @SerialName("runtime")
    val runtime: Any?,
    @SerialName("season_number")
    val seasonNumber: Int?,
    @SerialName("show_id")
    val showId: Int?,
    @SerialName("still_path")
    val stillPath: Any?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
)