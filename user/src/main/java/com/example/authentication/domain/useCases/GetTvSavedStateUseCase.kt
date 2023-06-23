package com.example.authentication.domain.useCases

import com.example.authentication.domain.repositories.UserRepository
import javax.inject.Inject


class GetTvSavedStateUseCase @Inject constructor(private val repo: UserRepository) {
    suspend operator fun invoke(seriesId: Int): Result<Boolean> {
        return try {
            Result.success(repo.getTvSavedState(seriesId))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

}
