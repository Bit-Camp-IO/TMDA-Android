package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.sharedComponent.auth.SessionProvider
import javax.inject.Inject

class GetBookMarkedSeriesUseCase @Inject constructor(
    private val repository: TvShowRepository,
    private val sessionProvider: SessionProvider
) {
    suspend fun invoke(page: Int): Result<TvShowPage> {
        return try {
            val sessionId = sessionProvider.getSessionId()
            Result.success(repository.getBookMarkedSeries(16874876, sessionId, page))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}

