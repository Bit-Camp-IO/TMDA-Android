package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.sharedComponent.auth.SessionProvider
import javax.inject.Inject

class AddOrRemoveTvFromWatchListUseCase @Inject constructor(
    private val repo: TvShowRepository,
    private val sessionProvider: SessionProvider
) {
    suspend fun invoke(seriesId: Int, isAddRequest: Boolean): Result<Unit> {
        return try {
            Result.success(repo.addSeriesToWatchList(sessionProvider.getSessionId(),seriesId, isAddRequest))
        } catch (e: Throwable) {
            Result.failure(e)
        }

    }
}