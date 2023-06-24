package com.example.moviesComponent.domain.useCases

import com.example.moviesComponent.domain.repositories.MoviesRepository
import com.example.sharedComponent.auth.SessionProvider
import javax.inject.Inject

class GetMovieSavedStateUseCase @Inject constructor(
    private val repo: MoviesRepository,
    private val sessionProvider: SessionProvider
) {
    suspend fun invoke(movieId: Int): Result<Boolean> {
        return try {
            Result.success(repo.getMovieSavedState(movieId, sessionProvider.getSessionId()))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}