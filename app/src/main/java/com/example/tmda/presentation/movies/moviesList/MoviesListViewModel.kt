package com.example.tmda.presentation.movies.moviesList

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.authentication.domain.entities.User
import com.example.authentication.domain.interactors.GetCurrentUserUseCase
import com.example.movies.domain.useCases.AddMovieToWatchListUseCase
import com.example.movies.domain.useCases.GetMovieSavedStateUseCase
import com.example.movies.domain.useCases.MovieUseCaseFactory
import com.example.tmda.presentation.movies.paging.MovieWithBookMarkPagingProvider
import com.example.tmda.presentation.movies.uiModels.MovieUiDto
import com.example.tmda.presentation.movies.uiModels.MoviesScreenType
import com.example.tmda.presentation.navigation.MOVIES_LIST_SCREEN_TYPE
import com.example.tmda.presentation.navigation.MOVIE_ID
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
    private val userUseCase: GetCurrentUserUseCase,
    private val getMovieSavedStateUseCase: GetMovieSavedStateUseCase,
    private val addMovieToWatchListUseCase: AddMovieToWatchListUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val moviesScreenType: MoviesScreenType = savedStateHandle[MOVIES_LIST_SCREEN_TYPE]!!
    private val movieId: Int? = savedStateHandle[MOVIE_ID]
    private lateinit var user: User
    private var moviesUseCase: MovieUseCaseFactory.BaseUseCase
    private var pagesStream: Flow<PagingData<MovieUiDto>>? = null

    init {
        viewModelScope.launch(Dispatchers.IO) { user = userUseCase.invoke() }
        moviesUseCase = moviesScreenType.toUseCase()
    }

    fun getPagesStream(): Flow<PagingData<MovieUiDto>> {
        if (pagesStream == null) {
            val movieWithBookMarkPagingProvider = MovieWithBookMarkPagingProvider(
                viewModelScope,
                moviesUseCase::invoke
            ) { handleUpdateSavedError(getMovieSavedStateUseCase.invoke(it, user.sessionId)) }
            pagesStream =
                movieWithBookMarkPagingProvider.createPager().flow.cachedIn(viewModelScope)
        }
        return pagesStream!!
    }

    suspend fun addOrRemoveMovieToSavedList(movieId: Int, isSaved:MutableState<Boolean>): Boolean {

        addMovieToWatchListUseCase.invoke(16874876, user.sessionId, movieId, !isSaved.value).onSuccess {
            isSaved.value=!isSaved.value
            return true
        }
        return false
    }

    fun updateIsSavedState(movies: List<MovieUiDto>, priorityRange: IntRange) {
        viewModelScope.launch(Dispatchers.IO) {
            priorityRange.map {
                async {
                    if (movies.isEmpty()) return@async
                    val movie = movies[it]
                    val isSaved = getMovieSavedStateUseCase.invoke(movie.id, user.sessionId)
                    movie.isSaved.value = handleUpdateSavedError(isSaved)
                }
            }.awaitAll()


            movies.forEachIndexed { index, movie ->
                launch lowPriority@{
                    if (index in priorityRange) return@lowPriority
                    val result = getMovieSavedStateUseCase.invoke(movie.id, user.sessionId)
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
    }
}