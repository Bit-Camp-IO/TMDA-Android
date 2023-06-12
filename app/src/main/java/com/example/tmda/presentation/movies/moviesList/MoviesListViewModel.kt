package com.example.tmda.presentation.movies.moviesList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.authentication.domain.entities.User
import com.example.authentication.domain.interactors.GetCurrentUserUseCase
import com.example.movies.domain.interactors.AddMovieToWatchListUseCase
import com.example.movies.domain.interactors.GetMovieSavedStateUseCase
import com.example.movies.domain.interactors.GetMoviesWithTypeInteractor
import com.example.tmda.presentation.movies.paging.MovieWithBookMarkPageProvider
import com.example.tmda.presentation.navigation.MOVIES_LIST_SCREEN_ID
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
    private val interactor: GetMoviesWithTypeInteractor,
    private val userUseCase: GetCurrentUserUseCase,
    private val getMovieSavedStateUseCase: GetMovieSavedStateUseCase,
    private val addMovieToWatchListUseCase: AddMovieToWatchListUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val screenType: ScreenType = savedStateHandle[MOVIES_LIST_SCREEN_ID]!!
    private val movieId: Int? = savedStateHandle[MOVIE_ID]
    private lateinit var user: User
    private var moviesUseCase: GetMoviesWithTypeInteractor.BaseUseCase
    private var pagesStream: Flow<PagingData<MovieUiDto>>? = null



    init {
        viewModelScope.launch(Dispatchers.IO) { user = userUseCase.invoke() }
        moviesUseCase = screenType.toUseCase()
    }

    fun getPagesStream(): Flow<PagingData<MovieUiDto>> {
        if (pagesStream == null) {
            val movieWithBookMarkPageProvider = MovieWithBookMarkPageProvider(
                viewModelScope,
                moviesUseCase::invoke
            ) { getMovieSavedStateUseCase.invoke(it, user.sessionId) }
            pagesStream = movieWithBookMarkPageProvider.createPager().flow .cachedIn(viewModelScope)
        }
        return pagesStream!!
    }

    fun addOrRemoveMovieToSavedList(movieId: Int, isSaved: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            addMovieToWatchListUseCase.invoke(16874876, user.sessionId, movieId, !isSaved)
        }
    }

    fun updateIsSavedState(movies: List<MovieUiDto>, priorityRange: IntRange) {
        viewModelScope.launch(Dispatchers.IO) {
          priorityRange.map {
                async {
                    val movie = movies[it]
                    val isSaved=getMovieSavedStateUseCase.invoke(movie.id, user.sessionId)
                    movie.isSaved.value = isSaved
                    isSaved
                }
            }.awaitAll()


            movies.forEachIndexed { index, movie ->
                launch lowPriority@{
                    if (index in priorityRange) return@lowPriority
                    movie.isSaved.value =
                        getMovieSavedStateUseCase.invoke(movie.id, user.sessionId)
                }
            }
        }
    }

    private fun ScreenType.toUseCase() = when (this) {

        ScreenType.Upcoming -> interactor.invoke(GetMoviesWithTypeInteractor.MovieType.Upcoming)
        ScreenType.NowPlaying -> interactor.invoke(GetMoviesWithTypeInteractor.MovieType.NowPlaying)
        ScreenType.TopRated -> interactor.invoke(GetMoviesWithTypeInteractor.MovieType.TopRated)
        ScreenType.Popular -> interactor.invoke(GetMoviesWithTypeInteractor.MovieType.Popular)
        ScreenType.Recommended -> interactor.invoke(
            GetMoviesWithTypeInteractor.MovieTypeWithId.Recommended,
            movieId!!
        )

        ScreenType.Similar -> interactor.invoke(
            GetMoviesWithTypeInteractor.MovieTypeWithId.Similar,
            movieId!!
        )
    }
}