package com.example.user.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionDto(
    @Json(name = "success") val isSucceeded: Boolean,
    @Json(name = "session_id") val sessionId: String,
)
