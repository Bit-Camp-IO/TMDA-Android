package com.example.sharedComponent.auth

interface SessionProvider {
    suspend fun getSessionId(): String
}