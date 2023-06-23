package com.example.authentication.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteDto(
    val success: Boolean

)
