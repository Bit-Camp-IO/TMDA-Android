package com.bitIO.tvshowcomponent.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)