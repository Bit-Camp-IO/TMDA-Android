package com.example.authentication.domain.useCases

import com.example.authentication.domain.entities.User
import com.example.authentication.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetSessionStateStreamUseCase @Inject constructor(private val repo: UserRepository) {
    fun invoke(): Flow<User?> {
        return repo.getUserState()
    }
}