package com.example.authentication.domain.useCases

import com.example.authentication.domain.entities.User
import com.example.authentication.domain.repositories.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase  @Inject constructor(private val repo: UserRepository) {
    suspend fun invoke(): User {
       return repo.getCurrentUser()
    }
}