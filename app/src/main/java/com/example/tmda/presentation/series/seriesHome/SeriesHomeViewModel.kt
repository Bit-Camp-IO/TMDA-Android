package com.example.tmda.presentation.series.seriesHome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.useCases.GetTvShowDetailsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.tvShow.GetTrendingTvShowUseCase
import com.bitIO.tvshowcomponent.domain.useCases.tvShow.TvShowUseCaseFactory
import com.example.tmda.presentation.series.uiDto.TvShowUiModel
import com.example.tmda.presentation.series.uiDto.toTvShowUIModel
import com.example.tmda.presentation.shared.UiStates.UiState
import com.example.tmda.presentation.shared.UiStates.mapToOtherType
import com.example.tmda.presentation.shared.UiStates.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesHomeViewModel @Inject constructor(
    factory: TvShowUseCaseFactory,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val  trendingTvShowUseCase: GetTrendingTvShowUseCase
) : ViewModel() {
   private val nowPlayingUseCase = factory.getUseCase(TvShowUseCaseFactory.SeriesType.NowPlaying)
    private val topRatedUseCase = factory.getUseCase(TvShowUseCaseFactory.SeriesType.TopRated)
    private val popularUseCase = factory.getUseCase(TvShowUseCaseFactory.SeriesType.Popular)

    private val _trendingUiState: MutableState<UiState<List<TvShowUiModel>>> =
        mutableStateOf(UiState.Loading())
    val trendingUiState: State<UiState<List<TvShowUiModel>>>
        get() = _trendingUiState

    private val _topRatedUiState: MutableState<UiState<List<TvShowUiModel>>> =
        mutableStateOf(UiState.Loading())
    val topRatedUiState: State<UiState<List<TvShowUiModel>>>
        get() = _topRatedUiState

    private val _popularUiState: MutableState<UiState<List<TvShowUiModel>>> =
        mutableStateOf(UiState.Loading())
    val popularUiState: State<UiState<List<TvShowUiModel>>>
        get() = _popularUiState

    private val _onTheAirUiState: MutableState<UiState<List<TvShowUiModel>>> =
        mutableStateOf(UiState.Loading())
    val onTheAirUiState: State<UiState<List<TvShowUiModel>>>
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
        return _trendingUiState.value is UiState.Failure &&
                _topRatedUiState.value is UiState.Failure &&
                _popularUiState.value is UiState.Failure &&
                _onTheAirUiState.value is UiState.Failure

    }
}