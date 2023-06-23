package com.example.authentication.domain.useCases

import com.example.authentication.domain.entities.UserDetails
import com.example.authentication.domain.repositories.UserRepository
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