package com.example.tvshowfeature.seriesHome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.useCases.tvShow.GetTrendingTvShowUseCase
import com.bitIO.tvshowcomponent.domain.useCases.tvShow.TvShowUseCaseFactory
import com.example.sharedui.uiStates.mapToOtherType
import com.example.sharedui.uiStates.toUiState
import com.example.tvshowfeature.uiDto.TvShowUiModel
import com.example.tvshowfeature.uiDto.toTvShowUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesHomeViewModel @Inject constructor(
    factory: TvShowUseCaseFactory,
    private val  trendingTvShowUseCase: GetTrendingTvShowUseCase
) : ViewModel() {
   private val nowPlayingUseCase = factory.getUseCase(TvShowUseCaseFactory.SeriesType.NowPlaying)
    private val topRatedUseCase = factory.getUseCase(TvShowUseCaseFactory.SeriesType.TopRated)
    private val popularUseCase = factory.getUseCase(TvShowUseCaseFactory.SeriesType.Popular)

    private val _trendingUiState: MutableState<com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>> =
        mutableStateOf(com.example.sharedui.uiStates.UiState.Loading())
    val trendingUiState: State<com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>>
        get() = _trendingUiState

    private val _topRatedUiState: MutableState<com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>> =
        mutableStateOf(com.example.sharedui.uiStates.UiState.Loading())
    val topRatedUiState: State<com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>>
        get() = _topRatedUiState

    private val _popularUiState: MutableState<com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>> =
        mutableStateOf(com.example.sharedui.uiStates.UiState.Loading())
    val popularUiState: State<com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>>
        get() = _popularUiState

    private val _onTheAirUiState: MutableState<com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>> =
        mutableStateOf(com.example.sharedui.uiStates.UiState.Loading())
    val onTheAirUiState: State<com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>>
        get() = _onTheAirUiState

    init {
        updateAll()
    }

    fun updateAll() {
        updateTrending()
        updateTopRatedTvShows()
        updatePopularTvShows()
        updateOnTheAirTvShows()
    }

    private fun updateTrending() {
        viewModelScope.launch(Dispatchers.IO) {
            _trendingUiState.value =
                trendingTvShowUseCase.invoke()
                    .mapToOtherType { it -> it.results.take(5).map { it.toTvShowUIModel() } }.toUiState()

        }
    }

    private fun updateTopRatedTvShows() {
        viewModelScope.launch(Dispatchers.IO) {
            _topRatedUiState.value =
                topRatedUseCase.invoke(1)
                    .mapToOtherType { it -> it.results.map { it.toTvShowUIModel() } }.toUiState()
        }

    }

    private fun updatePopularTvShows() {
        viewModelScope.launch(Dispatchers.IO) {
            _popularUiState.value =
                popularUseCase.invoke(1)
                    .mapToOtherType { it -> it.results.map { it.toTvShowUIModel() } }.toUiState()

        }

    }

    private fun updateOnTheAirTvShows() {
        viewModelScope.launch(Dispatchers.IO) {
            _onTheAirUiState.value =
                nowPlayingUseCase.invoke(1)
                    .mapToOtherType { it -> it.results.map { it.toTvShowUIModel() } }.toUiState()
        }

    }

    fun isErrorState(): Boolean {
        return _trendingUiState.value is com.example.sharedui.uiStates.UiState.Failure &&
                _topRatedUiState.value is com.example.sharedui.uiStates.UiState.Failure &&
                _popularUiState.value is com.example.sharedui.uiStates.UiState.Failure &&
                _onTheAirUiState.value is com.example.sharedui.uiStates.UiState.Failure

    }
}