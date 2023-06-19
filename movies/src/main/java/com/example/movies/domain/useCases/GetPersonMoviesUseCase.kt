package com.example.movies.domain.useCases

import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetPersonMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend fun invoke(personId: Int): Result<List<Movie>> {
        return try {
            Result.success(repository.getPersonMovies(personId))
        } catch (e: Throwable) {
            throw e
            Result.failure(e)
        }
    }
}