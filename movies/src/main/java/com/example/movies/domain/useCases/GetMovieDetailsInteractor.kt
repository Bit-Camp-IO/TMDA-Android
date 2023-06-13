package com.example.movies.domain.useCases

import com.example.movies.domain.enities.movie.MovieDetails
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsInteractor @Inject constructor(private val repo: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): Result<MovieDetails> {
        return try {
            Result.success(repo.getMovieDetails(movieId))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
