package com.example.user.domain.useCases

import com.example.user.domain.entities.User
import com.example.user.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetSessionStateStreamUseCase @Inject constructor(private val repo: UserRepository) {
    fun invoke(): Flow<User?> {
        return repo.getUserState()
    }
}