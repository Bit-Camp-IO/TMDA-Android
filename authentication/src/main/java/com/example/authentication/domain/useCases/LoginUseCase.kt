package com.example.authentication.domain.useCases

import com.example.authentication.domain.repositories.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: UserRepository) {
    suspend fun invoke(userName: String, password: String) {
        repo.createLoginSession(userName, password).sessionId
    }
}