package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.mapper.toHomeDomain
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNowPlayingHomeTvShowsUseCase @Inject constructor(private val repository: TvShowRepository) {
    suspend operator fun invoke(): Flow<List<TvShow?>> {
        return repository.getNowPlayingHomeTvShows().map { it.map { it.toHomeDomain() } }
    }

}