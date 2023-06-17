package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.shared.entities.Video
import javax.inject.Inject

class GetTvVideosUseCase @Inject constructor(private val repo: TvShowRepository) {
    suspend fun invoke(id: Int): Result<List<Video>> {
        return try {
            Result.success(repo.getSeriesVideos(id))
        } catch (e: Throwable) {
            Result.failure(e)
        }

    }
}