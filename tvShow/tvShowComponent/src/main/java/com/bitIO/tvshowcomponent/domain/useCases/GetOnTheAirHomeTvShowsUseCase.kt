package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.mapper.toDomain
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOnTheAirHomeTvShowsUseCase  @Inject constructor(private val repository: TvShowRepository) {
    suspend operator fun invoke(): Flow<List<TvShow?>> {
        return repository.getOnTheAirHomeTvShows().map { it.map { it.toDomain() } }
    }

}