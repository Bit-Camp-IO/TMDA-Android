package com.example.user.domain.useCases

import com.example.user.domain.repositories.UserRepository
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