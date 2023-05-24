package com.bitIO.tvshowcomponent.domain.useCases

import android.util.Log
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.mapper.toDomain
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTopRatedTvShowUseCase @Inject constructor(
    private val repository: TvShowRepository
) {
    suspend operator fun invoke(): List<TvShow> {
        return repository.getTopRatedTvShow()?.toDomain() ?: emptyList()
    }

}