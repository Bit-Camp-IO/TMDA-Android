package com.example.tmda.presentation.series.seriesList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bitIO.tvshowcomponent.domain.useCases.tvShow.TvShowUseCaseFactory
import com.example.authentication.domain.interactors.AddOrRemoveTvFromWatchListUseCase
import com.example.authentication.domain.interactors.GetTvSavedStateUseCase
import com.example.tmda.presentation.movies.moviesList.UiPage
import com.example.tmda.presentation.navigation.SERIES_ID
import com.example.tmda.presentation.navigation.SERIES_LIST_SCREEN_TYPE
import com.example.tmda.presentation.series.uiDto.TvShowBookMarkUiModel
import com.example.tmda.presentation.series.uiDto.toTvShowUIModel
import com.example.tmda.presentation.shared.paging.PagingProvider
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
    private val seriesScreenType: SeriesScreenType = savedStateHandle[SERIES_LIST_SCREEN_TYPE]!!
    private val seriesId: Int? = savedStateHandle[SERIES_ID]
    private var pagesStream: Flow<PagingData<TvShowBookMarkUiModel>>? = null
    private val seriesUseCase = seriesScreenType.toUseCase()


    fun getTvShowStream(): Flow<PagingData<TvShowBookMarkUiModel>> {
        if (pagesStream == null) {
            viewModelScope.launch { }
            pagesStream =
                PagingProvider(::getSeriesPagingData).createNewPager().flow.cachedIn(viewModelScope)
        }
        return pagesStream!!

    }

    private suspend fun getSeriesPagingData(pageIndex: Int): Result<UiPage<TvShowBookMarkUiModel>> {
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
                    UiPage(
                        page = seriesPage.page, results = tvShowBookMarkUiModels,
                        totalPages = seriesPage.totalPages, isError = false
                    )
                )
            } catch (e: Throwable) {
                Result.failure(e)
            }

        }.await()
    }

    private suspend fun getIsSavedState(seriesId: Int) =
        viewModelScope.async { getTvSavedStateUseCase.invoke(seriesId) }

   suspend fun addOrRemoveToBookMark(id:Int,isSaved:Boolean): Boolean {
        return viewModelScope.async{
        val result=    addOrRemoveTvFromWatchListUseCase.invoke(id,!isSaved)
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
                    tvShow.bookMarkState .value = handleUpdateSavedError(result)

                }
            }
        }
    }
    private fun handleUpdateSavedError(result: Result<Boolean>): Boolean {
        return if (result.isSuccess) result.getOrNull()!! else false

    }

}