package com.example.movies.domain.useCases

import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieSavedStateUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend fun invoke(movieId: Int, sessionId: String): Result<Boolean> {
        return try {
            Result.success(repo.getMovieSavedState(movieId, sessionId))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}