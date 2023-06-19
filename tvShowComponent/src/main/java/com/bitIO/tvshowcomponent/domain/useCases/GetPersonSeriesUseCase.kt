package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import javax.inject.Inject

class GetPersonSeriesUseCase @Inject constructor(
    private val repository: TvShowRepository
) {

    suspend fun invoke(personId: Int): Result<List<TvShow>> {
        return try {
            Result.success(repository.getPersonSeries(personId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}