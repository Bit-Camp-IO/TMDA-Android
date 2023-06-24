package com.example.moviesComponent.domain.useCases


import com.example.moviesComponent.domain.repositories.MoviesRepository
import com.example.sharedComponent.entities.credits.Credits
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(private val repo: MoviesRepository) {

    suspend fun invoke(movieId: Int): Result<Credits> {
        return try {
            Result.success(repo.getMovieCredits(movieId))
        } catch (e: Throwable) {
            Result.failure(e)
        }

    }

}