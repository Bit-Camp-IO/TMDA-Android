package com.example.authentication.domain.repositories

import com.example.authentication.domain.entities.GuestAuth
import com.example.authentication.domain.entities.UserAuth
import okhttp3.RequestBody

interface AuthRepository {
    suspend fun createGuestSession(): GuestAuth

    suspend fun createLoginSession(userName: String, password: String): UserAuth

    suspend fun deleteSession(body: RequestBody): Boolean
}