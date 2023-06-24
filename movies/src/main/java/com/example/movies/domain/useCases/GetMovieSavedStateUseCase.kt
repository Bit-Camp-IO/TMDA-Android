package com.example.movies.domain.useCases

import com.example.movies.domain.repositories.MoviesRepository
import com.example.shared.auth.SessionProvider
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