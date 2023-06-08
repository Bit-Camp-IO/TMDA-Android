package com.example.movies.domain.interactors

import com.example.movies.domain.enities.credits.Credits
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend fun invoke(movieId: Int): Credits = repo.getMovieCredits(movieId)

}