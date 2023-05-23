package com.example.authentication.data.dto

import com.squareup.moshi.Json

data class GuestSessionDto(
    @Json(name = "success") val isSucceeded: Boolean,
    @Json(name = "guest_session_id") val sessionId: String,
    @Json(name = "expires_at") val expirationDate: String,
)
