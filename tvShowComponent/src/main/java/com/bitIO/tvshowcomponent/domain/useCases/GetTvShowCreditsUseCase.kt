package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.shared.entities.credits.Credits
import javax.inject.Inject

class GetTvShowCreditsUseCase @Inject constructor(private val repository: TvShowRepository) {
    suspend operator fun invoke(id: Int): Result<Credits> {
        return try {
            Result.success(repository.getCredits(id))
        } catch (e: Throwable) {

            Result.failure(e)
        }

    }

}