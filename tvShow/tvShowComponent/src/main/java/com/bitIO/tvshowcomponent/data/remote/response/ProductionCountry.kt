package com.bitIO.tvshowcomponent.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
data class ProductionCountry(
    @Json(name = "iso_3166_1")
    val iso31661: String?,
    @Json(name = "name")
    val name: String?
)