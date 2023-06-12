package com.example.tmda.presentation.series.seriesDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.entity.Cast
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.useCases.GetTvShowCastUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetTvShowDetailsUseCase
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
    private val castUseCase: GetTvShowCastUseCase
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
}

data class DetailsUiState(
    var isDetailsLoading: Boolean = false,
    var tvShowDetails: TvShowDetails = TvShowDetails(),
    var tvShowError: String? = null,

    var isCastLoading: Boolean = false,
    var cast: List<Cast> = emptyList(),
    var castError: String? = null,
)