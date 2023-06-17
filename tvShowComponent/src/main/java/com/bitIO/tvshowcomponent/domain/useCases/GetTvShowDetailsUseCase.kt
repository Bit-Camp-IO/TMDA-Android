package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(private val repository: TvShowRepository) {
    suspend operator fun invoke(tvShowId: Int): Result<TvShowDetails> {
        return try {
            Result.success(repository.getTvShowDetails(tvShowId))
        } catch (e: Throwable) {

            Result.failure(e)
        }


    }

}