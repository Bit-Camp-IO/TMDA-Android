package com.bitIO.tvshowcomponent.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangesResult(
    @SerialName("adult")
    val adult: Boolean?,
    @SerialName("id")
    val id: Int?
)