package com.example.moviesComponent.domain.useCases

import com.example.moviesComponent.domain.enities.movie.MoviesPage
import com.example.moviesComponent.domain.repositories.MoviesRepository
import com.example.sharedComponent.auth.SessionProvider
import javax.inject.Inject

class GetBookMarkedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    private val sessionProvider: SessionProvider
) {
    suspend fun invoke(page: Int): Result<MoviesPage> {
        return try {
            val sessionId = sessionProvider.getSessionId()
            Result.success(repository.getBookMarkedMovies(16874876, sessionId, page))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}

