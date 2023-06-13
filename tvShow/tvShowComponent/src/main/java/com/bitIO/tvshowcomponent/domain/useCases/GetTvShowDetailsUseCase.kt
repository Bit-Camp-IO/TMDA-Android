package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.mapper.toDomain
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(private val repository: TvShowRepository) {
    suspend operator fun invoke(tvShowId: Int): Flow<TvShowDetails> {
        return repository.getTvShowDetails(tvShowId).map { it.toDomain() }
    }

}