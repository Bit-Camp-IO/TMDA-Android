package com.example.authentication.domain.entities

data class UserAuth(
    val userName: String,
    val password: String,
    val sessionId: String,
)
