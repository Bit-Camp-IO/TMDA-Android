package com.example.authentication.domain.useCases

import com.example.authentication.domain.repositories.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val repo: UserRepository) {
    suspend fun invoke(): Result<Boolean> {
        return try {
            Result.success(repo.deleteSession())
        }catch (e:Throwable){
            Result.failure(e)
        }

    }
}