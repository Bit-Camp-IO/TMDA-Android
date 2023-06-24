package com.example.user.domain.useCases

import com.example.user.domain.repositories.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: UserRepository) {
    suspend operator fun invoke(userName: String, password: String): Result<Unit> {
        return try {
            repo.createLoginSession(userName, password)
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }

    }
}