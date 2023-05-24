package com.bitIO.tvshowcomponent.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatedBy(
    @SerialName("credit_id")
    val creditId: String?,
    @SerialName("gender")
    val gender: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("profile_path")
    val profilePath: Any?
)