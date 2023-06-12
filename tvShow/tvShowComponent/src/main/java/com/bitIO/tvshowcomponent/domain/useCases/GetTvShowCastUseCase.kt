package com.bitIO.tvshowcomponent.domain.useCases

import com.bitIO.tvshowcomponent.domain.entity.Cast
import com.bitIO.tvshowcomponent.domain.mapper.toDomain
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTvShowCastUseCase @Inject constructor(private val repository: TvShowRepository) {
    suspend operator fun invoke(id: Int): Flow<List<Cast>> {
        return repository.getCredits(id).map {
            it.castDto?.map { it?.toDomain()!! }!!
        }
    }

}