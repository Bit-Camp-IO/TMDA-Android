package com.example.movies.domain.useCases


import com.example.movies.domain.repositories.MoviesRepository
import com.example.shared.entities.review.Review
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