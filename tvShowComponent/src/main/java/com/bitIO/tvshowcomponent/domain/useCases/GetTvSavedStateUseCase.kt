package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.shared.auth.SessionProvider
import javax.inject.Inject


class GetTvSavedStateUseCase @Inject constructor(
    private val repo: TvShowRepository,
    private val sessionProvider: SessionProvider
) {
    suspend operator fun invoke(seriesId: Int): Result<Boolean> {
        return try {
            Result.success(repo.getTvSavedState(seriesId, sessionProvider.getSessionId()))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

}
