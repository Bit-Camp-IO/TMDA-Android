package com.bitIO.tvshowcomponent.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
data class ChangesResult(
    @Json(name = "adult")
    val adult: Boolean?,
    @Json(name = "id")
    val id: Int?
)