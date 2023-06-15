package com.example.tmda.presentation.series.seriesDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.bitIO.tvshowcomponent.domain.useCases.GetTvShowCreditsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetTvShowDetailsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetTvVideosUseCase
import com.bitIO.tvshowcomponent.domain.useCases.tvShow.TvShowUseCaseFactory
import com.example.shared.entities.Video
import com.example.shared.entities.credits.Credits
import com.example.tmda.presentation.navigation.SERIES_ID
import com.example.tmda.presentation.series.seriesDetails.uiDto.OverView
import com.example.tmda.presentation.series.seriesDetails.uiDto.makeOverView
import com.example.tmda.presentation.shared.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias DResult<T> = Deferred<Result<T>>


@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    private val detailsUseCase: GetTvShowDetailsUseCase,
    private val creditsUseCase: GetTvShowCreditsUseCase,
    private val videosUseCase: GetTvVideosUseCase,
    tvShowUseCaseFactory: TvShowUseCaseFactory,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val seriesId = savedStateHandle.get<Int>(SERIES_ID)!!
    private val tvShowUseCase =
        tvShowUseCaseFactory.getUseCase(TvShowUseCaseFactory.SeriesType.Similar, movieId = seriesId)
    private val _overView: MutableState<UiState<OverView>> =
        mutableStateOf(UiState.Loading())
    val overView: State<UiState<OverView>>
        get() = _overView

    private val _seasons: MutableState<UiState<OverView>> =
        mutableStateOf(UiState.Loading())
    val seasons: State<UiState<OverView>>
        get() = _seasons


    init {
        updateOverView()
    }

    private fun updateOverView() {
        viewModelScope.launch(Dispatchers.IO) {
            val detailsDeferred = getTvShowDetails()
            val creditsDeferred = getTvShowCredits(seriesId)
            val similarDeferred = getSimilarTvShows()
            val videosDeferred = getTvShowVideos(seriesId)
            joinAll(
                detailsDeferred, creditsDeferred,
                similarDeferred, videosDeferred
            )
            mapDataToOverView(detailsDeferred, creditsDeferred, similarDeferred, videosDeferred)

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun mapDataToOverView(
        detailsDef: DResult<TvShowDetails>,
        creditsDef: DResult<Credits>,
        similarDef: DResult<TvShowPage>,
        videosDef: DResult<List<Video>>
    ) {
        _overView.value =
            try {
                val details = detailsDef.getCompleted().getOrThrow()
                val credits = creditsDef.getCompleted().getOrThrow()
                val similar = similarDef.getCompleted().getOrThrow().results
                val videos = videosDef.getCompleted().getOrThrow()

                UiState.Success(makeOverView(details, videos, credits, similar))
            } catch (e: Throwable) {
                UiState.Failure(e.message ?: "Unknown Error")
            }

    }


    private fun getTvShowDetails(): Deferred<Result<TvShowDetails>> {
        return viewModelScope.async(Dispatchers.IO) {
            detailsUseCase.invoke(seriesId)
        }
    }

    private fun getTvShowCredits(id: Int): Deferred<Result<Credits>> {
        return viewModelScope.async(Dispatchers.IO) {
            creditsUseCase.invoke(id)
        }
    }

    private fun getSimilarTvShows(): Deferred<Result<TvShowPage>> {
        return viewModelScope.async(Dispatchers.IO) {
            tvShowUseCase.invoke(1)
        }

    }

    private fun getTvShowVideos(id: Int): Deferred<Result<List<Video>>> {
        return viewModelScope.async {
            videosUseCase.invoke(id)
        }
    }



}


