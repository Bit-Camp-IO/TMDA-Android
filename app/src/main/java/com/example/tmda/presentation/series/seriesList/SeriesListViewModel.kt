package com.example.tmda.presentation.series.seriesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.useCases.GetPagingTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SeriesListViewModel @Inject constructor(private val pagingUseCase: GetPagingTvShowsUseCase) : ViewModel() {

    var pagingTvShows : Flow<PagingData<TvShow>>?= null


    fun getPagingData(type: Int){
        pagingTvShows = pagingUseCase(type)
            .cachedIn(viewModelScope)
    }
}