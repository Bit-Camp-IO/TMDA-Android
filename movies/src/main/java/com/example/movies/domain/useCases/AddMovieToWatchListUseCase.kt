package com.example.movies.domain.useCases

import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class AddMovieToWatchListUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend fun invoke(
        accountId: Int,
        sessionId: String,
        movieId: Int,
        isSavedRequest: Boolean
    ): Result<Unit> {
        return try {
            Result.success(repo.addMovieToWatchList(accountId, sessionId, movieId, isSavedRequest))
        } catch (e: Throwable) {
            Result.failure(e)
        }

    }


}