package com.example.tmda.presentation.series.seriesList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bitIO.tvshowcomponent.domain.useCases.tvShow.TvShowUseCaseFactory
import com.example.tmda.presentation.movies.moviesList.UiPage
import com.example.tmda.presentation.navigation.SERIES_ID
import com.example.tmda.presentation.navigation.SERIES_LIST_SCREEN_TYPE
import com.example.tmda.presentation.series.uiDto.TvShowUiModel
import com.example.tmda.presentation.series.uiDto.toUiPage
import com.example.tmda.presentation.shared.mapToOtherType
import com.example.tmda.presentation.shared.paging.PagingProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesListViewModel @Inject constructor(
    private val useCaseFactory: TvShowUseCaseFactory,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val seriesScreenType: SeriesScreenType = savedStateHandle[SERIES_LIST_SCREEN_TYPE]!!
    private val seriesId: Int? = savedStateHandle[SERIES_ID]
    private var pagesStream: Flow<PagingData<TvShowUiModel>>? = null
    private val seriesUseCase = seriesScreenType.toUseCase()



    fun getTvShowStream(): Flow<PagingData<TvShowUiModel>> {
        if (pagesStream==null){
            viewModelScope.launch {  }
            pagesStream=PagingProvider(::getSeriesPagingData).createPager().flow.cachedIn(viewModelScope)
        }
        return pagesStream!!

    }
    private suspend fun getSeriesPagingData(pageIndex:Int): Result<UiPage<TvShowUiModel>> {
        return  viewModelScope.async(Dispatchers.IO) {
            seriesUseCase.invoke(pageIndex).mapToOtherType { it.toUiPage() }
        }.await()
    }


    private fun SeriesScreenType.toUseCase() = when (this) {
        SeriesScreenType.OnTheAir -> useCaseFactory.getUseCase(TvShowUseCaseFactory.SeriesType.OnTheAir)
        SeriesScreenType.NowPlaying -> useCaseFactory.getUseCase(TvShowUseCaseFactory.SeriesType.NowPlaying)
        SeriesScreenType.TopRated -> useCaseFactory.getUseCase(TvShowUseCaseFactory.SeriesType.TopRated)
        SeriesScreenType.Popular -> useCaseFactory.getUseCase(TvShowUseCaseFactory.SeriesType.Popular)
        SeriesScreenType.Recommended -> useCaseFactory.getUseCase(
            TvShowUseCaseFactory.SeriesType.Recommended,
            seriesId!!
        )

        SeriesScreenType.Similar -> useCaseFactory.getUseCase(
            TvShowUseCaseFactory.SeriesType.Similar, seriesId!!
        )
    }


}