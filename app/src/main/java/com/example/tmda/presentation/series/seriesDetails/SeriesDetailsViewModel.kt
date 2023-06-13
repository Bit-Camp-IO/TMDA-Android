package com.example.tmda.presentation.series.seriesDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.entity.Cast
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.useCases.GetSimilarTvShowsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetTvShowCastUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetTvShowDetailsUseCase
import com.example.tmda.presentation.series.seriesHome.TvShowInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    private val detailsUseCase: GetTvShowDetailsUseCase,
    private val castUseCase: GetTvShowCastUseCase,
    private val similarTvShowsUseCase: GetSimilarTvShowsUseCase
) : ViewModel() {
    private var _detailsUiState = MutableStateFlow(DetailsUiState())
    val detailsUiState = _detailsUiState.asStateFlow()


    fun getTvShowDetails(id: Int) {
        viewModelScope.launch {
            _detailsUiState.update { it.copy(isDetailsLoading = true) }
            detailsUseCase(id).catch { throwable ->
                _detailsUiState.update { it.copy(isDetailsLoading = false, tvShowError = throwable.message) }
            }.collectLatest { tvShowDetails ->
                _detailsUiState.update { it.copy(isDetailsLoading = false, tvShowDetails = tvShowDetails) }
            }
        }
    }


    fun getTvShowCast(id: Int) {
        viewModelScope.launch {
            _detailsUiState.update { it.copy(isCastLoading = true) }
            castUseCase(id).catch { throwable ->
                _detailsUiState.update { it.copy(isCastLoading = false, castError = throwable.message) }
            }.collectLatest { tvShowCast ->
                _detailsUiState.update { it.copy(isCastLoading = false, cast = tvShowCast) }
            }
        }
    }

    fun getSimilarTvShows(id: Int) {
        val showDetails = arrayListOf<TvShowInfo>()
        viewModelScope.launch {
            _detailsUiState.update { it.copy(isSimilarLoading = true) }
            similarTvShowsUseCase(id).catch { throwable ->
                _detailsUiState.update { it.copy(isSimilarLoading = false, similarError = throwable.message) }
            }.collectLatest { tvShows ->
                tvShows.forEach { tvShow ->
                    detailsUseCase(tvShow?.id!!).collectLatest { tvShowDetails ->
                        showDetails.add(TvShowInfo(tvShow, tvShowDetails))
                    }
                }
                _detailsUiState.update { it.copy(isSimilarLoading = false, similarTvShows = showDetails) }
            }
        }
    }
}

data class DetailsUiState(
    var isDetailsLoading: Boolean = false,
    var tvShowDetails: TvShowDetails = TvShowDetails(),
    var tvShowError: String? = null,

    var isCastLoading: Boolean = false,
    var cast: List<Cast> = emptyList(),
    var castError: String? = null,

    var similarTvShows: List<TvShowInfo> = emptyList(),
    var isSimilarLoading: Boolean = false,
    var similarError: String? = null,


)