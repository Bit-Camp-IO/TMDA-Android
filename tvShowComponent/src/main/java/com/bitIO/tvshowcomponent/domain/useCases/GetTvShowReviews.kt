package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.shared.entities.review.Review
import javax.inject.Inject

class GetTvShowReviews @Inject constructor(private val repository: TvShowRepository) {

    suspend fun invoke(seriesId: Int): Result<List<Review>> {
        return try {
            Result.success(repository.getTvShowReviews(seriesId))

        } catch (e: Throwable) {
            Result.failure(e)
        }

    }
}