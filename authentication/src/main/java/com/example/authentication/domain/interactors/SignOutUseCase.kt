package com.example.authentication.domain.interactors

import com.example.authentication.domain.repositories.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val repo: UserRepository) {
    suspend fun invoke(): Boolean {
        return repo.deleteSession()
    }
}