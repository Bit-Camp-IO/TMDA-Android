package com.example.user.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestTokenDetailsDto(
    @Json(name = "success") val isSucceeded: Boolean,
    @Json(name = "request_token") val requestToken: String,
    @Json(name = "expires_at") val expirationDate: String,
)
