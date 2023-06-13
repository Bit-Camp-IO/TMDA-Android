package com.example.tmda.presentation.series.seriesHome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.useCases.GetNowPlayingHomeTvShowsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetOnTheAirHomeTvShowsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetPopularHomeTvShowsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetTopRatedHomeTvShowsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetTvShowDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesHomeViewModel @Inject constructor(
    private val getTopRatedHomeTvShowsUseCase: GetTopRatedHomeTvShowsUseCase,
    private val getNowPlayingHomeTvShowsUseCase: GetNowPlayingHomeTvShowsUseCase,
    private val getPopularHomeTvShowsUseCase: GetPopularHomeTvShowsUseCase,
    private val getOnTheAirHomeTvShowsUseCase: GetOnTheAirHomeTvShowsUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase
) : ViewModel() {
    private var _seriesUiState = MutableStateFlow(SeriesUiState())
    val seriesUiState = _seriesUiState.asStateFlow()


    init {
        getNowPlayingTvShows()
        getTopRatedTvShows()
        getPopularTvShows()
        getOnTheAirTvShows()
    }

    private fun getNowPlayingTvShows() {
        viewModelScope.launch {
            try {
                val showDetails = arrayListOf<TvShowInfo>()
                _seriesUiState.update { it.copy(isNowPlayingLoading = true) }
                getNowPlayingHomeTvShowsUseCase().collectLatest { tvShows ->
                    Log.d("getNowPlayingTvShows", "getNowPlayingTvShows: $tvShows")
                    tvShows.forEach { tvShow ->
                        tvShow?.id?.let {
                            getTvShowDetailsUseCase(it).collectLatest { tvShowDetails ->
                                showDetails.add(TvShowInfo(tvShow, tvShowDetails))
                            }

                        }
                    }
                    _seriesUiState.update {
                        it.copy(
                            isNowPlayingLoading = false,
                            tvShowInfo = showDetails
                        )
                    }

                }
            } catch (e: Exception) {
                _seriesUiState.update { it.copy(isNowPlayingLoading = false, nowPlayingErrorMsg = e.message!!) }
            }

        }
    }

    private fun getTopRatedTvShows() {
        viewModelScope.launch {
            try {
                val showDetails = arrayListOf<TvShowInfo>()
                _seriesUiState.update { it.copy(isTopRatedLoading = true) }
                getTopRatedHomeTvShowsUseCase().collectLatest { tvShows ->
                    tvShows.forEach { tvShow ->
                        Log.d("getTopRatedTvShows", "getTopRatedTvShows: $tvShows")
                        tvShow.id.let {
                            getTvShowDetailsUseCase(it).collectLatest { tvShowDetails ->
                                showDetails.add(TvShowInfo(tvShow, tvShowDetails))
                            }

                        }
                    }
                    _seriesUiState.update {
                        it.copy(
                            isTopRatedLoading = false,
                            topRatedTvShows = showDetails
                        )
                    }

                }
            } catch (e: Exception) {
                _seriesUiState.update { it.copy(isTopRatedLoading = false, topRatedErrorMsg = e.message!!) }
            }

        }

    }

    private fun getPopularTvShows() {
        viewModelScope.launch {
            try {
                val showDetails = arrayListOf<TvShowInfo>()
                _seriesUiState.update { it.copy(isPopularLoading = true) }
                getPopularHomeTvShowsUseCase().collectLatest { tvShows ->
                    Log.d("getPopularTvShows", "getPopularTvShows: $tvShows")
                    tvShows.forEach { tvShow ->
                        tvShow?.id?.let {
                            getTvShowDetailsUseCase(it).collectLatest { tvShowDetails ->
                                showDetails.add(TvShowInfo(tvShow, tvShowDetails))
                            }

                        }
                    }
                    _seriesUiState.update {
                        it.copy(
                            isPopularLoading = false,
                            popularTvShows = showDetails
                        )
                    }

                }
            } catch (e: Exception) {
                _seriesUiState.update { it.copy(isPopularLoading = false, popularErrorMsg = e.message!!) }
            }

        }

    }

    private fun getOnTheAirTvShows() {
        viewModelScope.launch {
            try {
                val showDetails = arrayListOf<TvShowInfo>()
                _seriesUiState.update { it.copy(isOnTheAirLoading = true) }
                getOnTheAirHomeTvShowsUseCase().collectLatest { tvShows ->
                    tvShows.forEach { tvShow ->
                        tvShow?.id?.let {
                            getTvShowDetailsUseCase(it).collectLatest { tvShowDetails ->
                                showDetails.add(TvShowInfo(tvShow, tvShowDetails))
                            }

                        }
                    }
                    _seriesUiState.update {
                        it.copy(
                            isOnTheAirLoading = false,
                            onTheAirTvShows = showDetails
                        )
                    }

                }
            } catch (e: Exception) {
                _seriesUiState.update { it.copy(isOnTheAirLoading = false, onTheAirErrorMsg = e.message!!) }
            }

        }

    }
}


data class SeriesUiState(
    var isNowPlayingLoading: Boolean = false,
    var nowPlayingErrorMsg: String? = null,
    var tvShowInfo: List<TvShowInfo> = emptyList(),

    var isTopRatedLoading: Boolean = false,
    var topRatedErrorMsg: String? = null,
    var topRatedTvShows: List<TvShowInfo> = emptyList(),

    var isPopularLoading: Boolean = false,
    var popularErrorMsg: String? = null,
    var popularTvShows: List<TvShowInfo> = emptyList(),

    var isOnTheAirLoading: Boolean = false,
    var onTheAirErrorMsg: String? = null,
    var onTheAirTvShows: List<TvShowInfo> = emptyList(),
)

data class TvShowInfo(
    val tvShow: TvShow? = null,
    val tvShowDetails: TvShowDetails? = null,
)