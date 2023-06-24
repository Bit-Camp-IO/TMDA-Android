package com.example.movies.domain.useCases

import com.example.movies.domain.repositories.MoviesRepository
import com.example.shared.auth.SessionProvider
import javax.inject.Inject

class AddMovieToWatchListUseCase @Inject constructor(
    private val repo: MoviesRepository,
    private val sessionProvider: SessionProvider
) {
    suspend fun invoke(

        movieId: Int,
        isSavedRequest: Boolean
    ): Result<Unit> {
        return try {
            Result.success(
                repo.addMovieToWatchList(
                    16874876,
                    sessionProvider.getSessionId(),
                    movieId,
                    isSavedRequest
                )
            )
        } catch (e: Throwable) {
            Result.failure(e)
        }

    }


}