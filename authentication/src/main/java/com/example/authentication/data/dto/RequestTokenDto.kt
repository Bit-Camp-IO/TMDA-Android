package com.example.authentication.data.dto

import com.squareup.moshi.Json

data class RequestTokenDto(
    @Json(name = "success") val isSucceeded: Boolean,
    @Json(name = "request_token") val requestToken: String,
    @Json(name = "expires_at") val expirationDate: String,
)
