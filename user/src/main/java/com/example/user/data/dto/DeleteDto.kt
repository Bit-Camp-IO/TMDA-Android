package com.example.user.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteDto(
    val success: Boolean

)
