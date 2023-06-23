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
import com.bitIO.tvshowcomponent.domain.useCases.GetTvShowReviews
import com.bitIO.tvshowcomponent.domain.useCases.GetTvVideosUseCase
import com.bitIO.tvshowcomponent.domain.useCases.tvShow.TvShowUseCaseFactory
import com.example.user.domain.useCases.AddOrRemoveTvFromWatchListUseCase
import com.example.user.domain.useCases.GetTvSavedStateUseCase
import com.example.shared.entities.Video
import com.example.shared.entities.credits.Credits
import com.example.shared.entities.review.Review
import com.example.tmda.presentation.navigation.SERIES_ID
import com.example.tmda.presentation.series.seriesDetails.uiDto.OverView
import com.example.tmda.presentation.series.seriesDetails.uiDto.makeOverView
import com.example.tmda.presentation.shared.uiStates.UiState
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
    private val addOrRemoveTvFromWatchListUseCase: AddOrRemoveTvFromWatchListUseCase,
    private val getTvSavedStateUseCase: GetTvSavedStateUseCase,
    private val getTvShowReviews: GetTvShowReviews,
    tvShowUseCaseFactory: TvShowUseCaseFactory,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val seriesId = savedStateHandle.get<Int>(SERIES_ID)!!
    private val similarTvShowUseCase =
        tvShowUseCaseFactory.getUseCase(TvShowUseCaseFactory.SeriesType.Similar, movieId = seriesId)

    private val recommendedTvShowUseCase =
        tvShowUseCaseFactory.getUseCase(
            TvShowUseCaseFactory.SeriesType.Recommended,
            movieId = seriesId
        )
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

    fun updateOverView() {
        viewModelScope.launch(Dispatchers.IO) {
            val detailsDeferred = getTvShowDetails()
            val creditsDeferred = getTvShowCredits(seriesId)
            val similarDeferred = getSimilarTvShows()
            val recommendedDeferred = getRecommendedShows()
            val videosDeferred = getTvShowVideos(seriesId)
            val isBookmarkedDeferred = getIsBookmarked()
            val reviewsDeferred = getReviews()
            joinAll(
                detailsDeferred, creditsDeferred,
                similarDeferred, videosDeferred,
                isBookmarkedDeferred, recommendedDeferred,
                reviewsDeferred
            )
            mapDataToOverView(
                detailsDeferred,
                creditsDeferred,
                similarDeferred,
                recommendedDeferred,
                videosDeferred,
                isBookmarkedDeferred,
                reviewsDeferred
            )

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun mapDataToOverView(
        detailsDef: DResult<TvShowDetails>,
        creditsDef: DResult<Credits>,
        similarDef: DResult<TvShowPage>,
        recommendedDef: DResult<TvShowPage>,
        videosDef: DResult<List<Video>>,
        isSavedDef: DResult<Boolean>,
        reviewsDef: DResult<List<Review>>
    ) {
        _overView.value =
            try {
                val details = detailsDef.getCompleted().getOrThrow()
                val credits = creditsDef.getCompleted().getOrThrow()
                val similar = similarDef.getCompleted().getOrThrow().results
                val recommended = recommendedDef.getCompleted().getOrThrow().results
                val videos = videosDef.getCompleted().getOrThrow()
                val isSaved = isSavedDef.getCompleted().getOrThrow()
                val reviews= reviewsDef.getCompleted().getOrThrow()

                UiState.Success(
                    makeOverView(
                        details,
                        videos,
                        credits,
                        similar,
                        recommended,
                        isSaved,
                        reviews
                    )
                )
            } catch (e: Throwable) {
                UiState.Failure(e.message ?: "Unknown Error")
            }

    }


    private fun getTvShowDetails(): DResult<TvShowDetails> {
        return viewModelScope.async(Dispatchers.IO) {
            detailsUseCase.invoke(seriesId)
        }
    }

    private fun getTvShowCredits(id: Int): DResult<Credits> {
        return viewModelScope.async(Dispatchers.IO) {
            creditsUseCase.invoke(id)
        }
    }

    private fun getSimilarTvShows(): DResult<TvShowPage> {
        return viewModelScope.async(Dispatchers.IO) {
            similarTvShowUseCase.invoke(1)
        }

    }

    private fun getRecommendedShows(): DResult<TvShowPage> {
        return viewModelScope.async(Dispatchers.IO) {
            recommendedTvShowUseCase.invoke(1)
        }

    }

    private fun getTvShowVideos(id: Int): DResult<List<Video>> {
        return viewModelScope.async {
            videosUseCase.invoke(id)
        }
    }

    private fun getIsBookmarked(): DResult<Boolean> {
        return viewModelScope.async { getTvSavedStateUseCase.invoke(seriesId) }
    }

    private fun getReviews(): DResult<List<Review>> {
        return viewModelScope.async { getTvShowReviews.invoke(seriesId) }

    }

    fun addOrRemoveSeriesFromSaveList() {
        viewModelScope.launch {
            val isSaved = (_overView.value as UiState.Success).data.savedState

            val result = addOrRemoveTvFromWatchListUseCase.invoke(seriesId, !isSaved.value)
            if (result.isSuccess)
                isSaved.value = !isSaved.value
        }

    }
}