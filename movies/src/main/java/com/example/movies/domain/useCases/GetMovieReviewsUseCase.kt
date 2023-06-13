package com.example.movies.domain.useCases

import com.example.movies.domain.enities.review.Review
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): Result<List<Review>> {
        return try {
            Result.success(repo.getMovieReviews(movieId))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

}