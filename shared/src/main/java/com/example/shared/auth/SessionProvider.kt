package com.example.shared.auth

interface SessionProvider {
    suspend fun getSessionId(): String
}