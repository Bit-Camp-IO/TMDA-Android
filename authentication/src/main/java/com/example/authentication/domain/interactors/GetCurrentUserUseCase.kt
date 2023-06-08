package com.example.authentication.domain.interactors

import com.example.authentication.domain.entities.User
import com.example.authentication.domain.repositories.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase  @Inject constructor(private val repo: UserRepository) {
    suspend fun invoke(): User {
       return repo.getCurrentUser()
    }
}