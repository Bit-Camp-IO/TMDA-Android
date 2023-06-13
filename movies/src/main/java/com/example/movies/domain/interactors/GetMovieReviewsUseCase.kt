package com.example.movies.domain.interactors

import com.example.movies.domain.enities.review.Review
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): List<Review> = repo.getMovieReviews(movieId)

}