package com.example.authentication.domain.useCases

import com.example.authentication.domain.repositories.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val repo: UserRepository) {
    suspend fun invoke(): Boolean {
        return repo.deleteSession()
    }
}