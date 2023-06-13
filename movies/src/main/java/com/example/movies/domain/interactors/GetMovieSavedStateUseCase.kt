package com.example.movies.domain.interactors

import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieSavedStateUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend fun invoke(movieId: Int, sessionId: String): Boolean {
        return repo.getMovieSavedState(movieId, sessionId)
    }
}