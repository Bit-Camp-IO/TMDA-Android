package com.example.moviesComponent.domain.useCases

import com.example.moviesComponent.domain.enities.movie.Movie
import com.example.moviesComponent.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetPersonMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend fun invoke(personId: Int): Result<List<Movie>> {
        return try {
            Result.success(repository.getPersonMovies(personId))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}