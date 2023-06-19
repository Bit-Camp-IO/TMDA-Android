package com.example.movies.domain.useCases

import com.example.movies.domain.enities.movie.MoviesPage
import com.example.movies.domain.repositories.MoviesRepository
import com.example.shared.auth.SessionProvider
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

