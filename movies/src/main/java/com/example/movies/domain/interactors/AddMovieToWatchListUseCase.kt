package com.example.movies.domain.interactors

import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class AddMovieToWatchListUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend fun invoke(accountId: Int, sessionId: String, movieId: Int, isSavedRequest: Boolean) =
        repo.addMovieToWatchList(accountId, sessionId, movieId, isSavedRequest)

}