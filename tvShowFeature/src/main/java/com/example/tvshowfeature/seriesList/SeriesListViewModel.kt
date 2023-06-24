package com.example.tvshowfeature.seriesList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bitIO.tvshowcomponent.domain.useCases.AddOrRemoveTvFromWatchListUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetTvSavedStateUseCase
import com.bitIO.tvshowcomponent.domain.useCases.tvShow.TvShowUseCaseFactory
import com.example.tvshowfeature.navigation.SERIES_ID
import com.example.tvshowfeature.navigation.SERIES_LIST_SCREEN_TYPE
import com.example.tvshowfeature.uiDto.TvShowBookMarkUiModel
import com.example.tvshowfeature.uiDto.toTvShowUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesListViewModel @Inject constructor(
    private val useCaseFactory: TvShowUseCaseFactory,
    private val addOrRemoveTvFromWatchListUseCase: AddOrRemoveTvFromWatchListUseCase,
    private val getTvSavedStateUseCase: GetTvSavedStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val seriesScreenType: SeriesScreenType = savedStateHandle[SERIES_LIST_SCREEN_TYPE]!!
    private val seriesId: Int? = savedStateHandle[SERIES_ID]
    private var pagesStream: Flow<PagingData<TvShowBookMarkUiModel>>? = null
    private val seriesUseCase = seriesScreenType.toUseCase()


    fun getTvShowStream(): Flow<PagingData<TvShowBookMarkUiModel>> {
        if (pagesStream == null) {
            viewModelScope.launch { }
            pagesStream =
                com.example.sharedui.paging.PagingProvider(::getSeriesPagingData).createNewPager().flow.cachedIn(viewModelScope)
        }
        return pagesStream!!

    }

    private suspend fun getSeriesPagingData(pageIndex: Int): Result<com.example.sharedui.paging.UiPage<TvShowBookMarkUiModel>> {
        return viewModelScope.async(Dispatchers.IO) {
            try {
                val seriesPage = async { seriesUseCase.invoke(pageIndex) }.await().getOrThrow()
                val correspondingSavedList =
                    seriesPage.results.map { getIsSavedState(it.id) }.awaitAll()
                        .map { it.getOrThrow() }

                val tvShowBookMarkUiModels = seriesPage.results.mapIndexed { index, tvShow ->
                    TvShowBookMarkUiModel(
                        tvShowUiModel = tvShow.toTvShowUIModel(),
                        mutableStateOf(correspondingSavedList[index])
                    )
                }
                Result.success(
                    com.example.sharedui.paging.UiPage(
                        page = seriesPage.page, results = tvShowBookMarkUiModels,
                        totalPages = seriesPage.totalPages
                    )
                )
            } catch (e: Throwable) {
                Result.failure(e)
            }

        }.await()
    }

    private suspend fun getIsSavedState(seriesId: Int) =
        viewModelScope.async { getTvSavedStateUseCase.invoke(seriesId) }

    suspend fun addOrRemoveToBookMark(id: Int, isSaved: MutableState<Boolean>): Boolean {
        return viewModelScope.async {
            val result = addOrRemoveTvFromWatchListUseCase.invoke(id, !isSaved.value)
            if (result.isSuccess) {
                isSaved.value = !isSaved.value
            }
            result.isSuccess
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

        SeriesScreenType.Bookmarked -> useCaseFactory.getUseCase(TvShowUseCaseFactory.SeriesType.Bookmarked)
    }

    fun updateIsSavedState(series: List<TvShowBookMarkUiModel>, priorityRange: IntRange) {
        viewModelScope.launch(Dispatchers.IO) {
            priorityRange.map {
                async {
                    if (series.isEmpty()) return@async
                    val tvShow = series[it]
                    val isSaved = getIsSavedState(tvShow.tvShowUiModel.id).await()
                    tvShow.bookMarkState.value = handleUpdateSavedError(isSaved)
                }
            }.awaitAll()


            series.forEachIndexed { index, tvShow ->
                launch lowPriority@{
                    if (index in priorityRange) return@lowPriority
                    val result = getIsSavedState(tvShow.tvShowUiModel.id).await()
                    tvShow.bookMarkState.value = handleUpdateSavedError(result)

                }
            }
        }
    }

    private fun handleUpdateSavedError(result: Result<Boolean>): Boolean {
        return if (result.isSuccess) result.getOrNull()!! else false

    }

}