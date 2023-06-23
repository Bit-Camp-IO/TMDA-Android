package com.example.user.domain.useCases

import com.example.user.domain.entities.UserDetails
import com.example.user.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val repository: UserRepository) {
    suspend fun invoke(): Result<UserDetails> {
        return try {
            Result.success(repository.getUserDetails())
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

}