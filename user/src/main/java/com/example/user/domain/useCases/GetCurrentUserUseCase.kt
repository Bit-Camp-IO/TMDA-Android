package com.example.user.domain.useCases

import com.example.user.domain.entities.User
import com.example.user.domain.repositories.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase  @Inject constructor(private val repo: UserRepository) {
    suspend fun invoke(): User {
       return repo.getCurrentUser()
    }
}