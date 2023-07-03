package com.example.moviesfeature.moviesList

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesComponent.domain.enities.movie.MoviesPage
import com.example.moviesComponent.domain.useCases.AddMovieToWatchListUseCase
import com.example.moviesComponent.domain.useCases.GetMovieSavedStateUseCase
import com.example.moviesComponent.domain.useCases.MovieUseCaseFactory
import com.example.moviesfeature.navigation.MOVIES_LIST_SCREEN_TYPE
import com.example.moviesfeature.navigation.MOVIE_ID
import com.example.moviesfeature.uiModels.MovieUiDto
import com.example.moviesfeature.uiModels.MoviesScreenType
import com.example.sharedui.paging.PagingProvider
import com.example.sharedui.paging.UiPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val interactor: MovieUseCaseFactory,
    private val getMovieSavedStateUseCase: GetMovieSavedStateUseCase,
    private val addMovieToWatchListUseCase: AddMovieToWatchListUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val moviesScreenType: MoviesScreenType = savedStateHandle[MOVIES_LIST_SCREEN_TYPE]!!
    private val movieId: Int? = savedStateHandle[MOVIE_ID]
    private var moviesUseCase: MovieUseCaseFactory.BaseUseCase
    private var pagesStream: Flow<PagingData<MovieUiDto>>? = null

    init {

        moviesUseCase = moviesScreenType.toUseCase()
    }


    fun getPagesStream(): Flow<PagingData<MovieUiDto>> {
        if (pagesStream == null) {
            viewModelScope.launch { }
            pagesStream =
                PagingProvider(::getMovieDtoPage).createNewPager().flow.cachedIn(viewModelScope)
        }
        return pagesStream!!
    }

    private suspend fun getMovieDtoPage(pageIndex: Int): Result<UiPage<MovieUiDto>> {
        val moviesPage =
            viewModelScope.async { moviesUseCase.invoke(pageIndex) }.await()
                .getOrElse { return Result.failure(it) }
        val isSavedList =
            getIsSavedCorrespondingList(moviesPage).map { it.getOrElse { return Result.failure(it) } }

        val moviesUiDtoList = moviesPage.results.mapIndexed { index, movie ->
            MovieUiDto(movie, isSavedList[index])
        }

        return Result.success(
            UiPage(
                page = pageIndex, results = moviesUiDtoList,
                totalPages = moviesPage.totalPages,
            )
        )
    }

    private suspend fun getIsSavedCorrespondingList(moviesPage: MoviesPage): List<Result<Boolean>> {
        return viewModelScope.async(Dispatchers.IO) {
            moviesPage.results.map { movie ->
                viewModelScope.async(Dispatchers.IO) {
                    getMovieSavedStateUseCase.invoke(movie.id)
                }
            }.awaitAll()
        }.await()


    }


    suspend fun addOrRemoveMovieToSavedList(movieId: Int, isSaved: MutableState<Boolean>): Boolean {

        addMovieToWatchListUseCase.invoke( movieId, !isSaved.value)
            .onSuccess {
                isSaved.value = !isSaved.value
                return true
            }
        return false
    }

    fun updateIsSavedState(movies: List<MovieUiDto>, priorityRange: IntRange) {
        viewModelScope.launch(Dispatchers.IO) {
            priorityRange.map {
                async {
                   // if (movies.isEmpty()) return@async
                    val movie = movies.getOrNull(it) ?: return@async
                    val isSaved = getMovieSavedStateUseCase.invoke(movie.id)
                    movie.isSaved.value = handleUpdateSavedError(isSaved)
                }
            }.awaitAll()


            movies.forEachIndexed { index, movie ->
                launch lowPriority@{
                    if (index in priorityRange) return@lowPriority
                    val result = getMovieSavedStateUseCase.invoke(movie.id)
                    movie.isSaved.value = handleUpdateSavedError(result)

                }
            }
        }
    }

    private fun handleUpdateSavedError(result: Result<Boolean>): Boolean {
        return if (result.isSuccess) result.getOrNull()!! else false

    }

    private fun MoviesScreenType.toUseCase() = when (this) {

        MoviesScreenType.Upcoming -> interactor.getUseCase(MovieUseCaseFactory.MovieType.Upcoming)
        MoviesScreenType.NowPlaying -> interactor.getUseCase(MovieUseCaseFactory.MovieType.NowPlaying)
        MoviesScreenType.TopRated -> interactor.getUseCase(MovieUseCaseFactory.MovieType.TopRated)
        MoviesScreenType.Popular -> interactor.getUseCase(MovieUseCaseFactory.MovieType.Popular)
        MoviesScreenType.Recommended -> interactor.getUseCase(
            MovieUseCaseFactory.MovieTypeWithId.Recommended,
            movieId!!
        )

        MoviesScreenType.Similar -> interactor.getUseCase(
            MovieUseCaseFactory.MovieTypeWithId.Similar,
            movieId!!
        )
        MoviesScreenType.Bookmarked -> interactor.getUseCase(MovieUseCaseFactory.MovieType.Bookmarked)
    }
}