package com.bitIO.tvshowcomponent.domain.useCases.tvShow

import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTrendingTvShowUseCase @Inject constructor(private val repository: TvShowRepository) {
    suspend fun invoke(): Result<TvShowPage> {
        return try {
            Result.success(repository.getTrendingTvShows())
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}