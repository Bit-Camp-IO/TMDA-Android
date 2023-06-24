package com.example.user.domain.useCases

import com.example.user.domain.repositories.UserRepository
import javax.inject.Inject

class GetIsFirstUserLoginUseCase @Inject constructor(private val repository: UserRepository) {
    suspend fun invoke(): Result<Boolean> {
        return try {
            Result.success(repository.getIsFirstUserLogin())
        } catch (e: Throwable) {
            Result.success(true)
        }

    }
}