package com.bitIO.tvshowcomponent.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
data class Network(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "logo_path")
    val logoPath: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "origin_country")
    val originCountry: String?
)