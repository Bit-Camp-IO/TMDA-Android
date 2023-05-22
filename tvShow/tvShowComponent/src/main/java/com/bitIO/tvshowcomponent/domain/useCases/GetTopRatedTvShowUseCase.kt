package com.bitIO.tvshowcomponent.domain.useCases

import android.util.Log
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTopRatedTvShowUseCase @Inject constructor(
    private val repository: TvShowRepository
) {
    suspend operator fun invoke(): String {
        val result = repository.getTopRatedTvShow()
        Log.d("TESTTTTTTTTTT","result = ${result.toString()}\n\n")
        return result.toString()
    }
}