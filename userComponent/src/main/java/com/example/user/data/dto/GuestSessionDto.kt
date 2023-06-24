package com.example.user.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GuestSessionDto(
    @Json(name = "success") val isSucceeded: Boolean,
    @Json(name = "guest_session_id") val sessionId: String,
    @Json(name = "expires_at") val expirationDate: String,
)
