package com.example.authentication.domain.entities

data class GuestAuth(
    val sessionId: String,
    val expirationDate: String,
)
