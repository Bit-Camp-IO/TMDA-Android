package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import javax.inject.Inject

class SearchSeriesUseCase
@Inject constructor(
    private val repo: TvShowRepository
) {
    suspend fun invoke(keyword: String, page: Int): Result<TvShowPage> {
        return try {
            Result.success(repo.searchSeries(keyword = keyword, includeAdults = true, page = page))
        } catch (e: Throwable) {
            throw e
            Result.failure(e)
        }

    }
}
