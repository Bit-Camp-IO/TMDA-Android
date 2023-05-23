package com.example.authentication.data.dto

import com.squareup.moshi.Json

data class SessionDto(
    @Json(name = "success") val isSucceeded: Boolean,
    @Json(name = "session_id") val sessionId: String,
)
