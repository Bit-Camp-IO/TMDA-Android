package com.bitIO.tvshowcomponent.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditsResponse(
    @Json(name = "cast")
    val castDto: List<CastDto?>? = null,
    @Json(name = "crew")
    val crew: List<Crew?>? = null,
    @Json(name = "id")
    val id: Int? = null
)