package com.example.tmda.presentation.movies.moviesList

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.authentication.domain.entities.User
import com.example.authentication.domain.interactors.GetCurrentUserUseCase
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.interactors.AddMovieToWatchListUseCase
import com.example.movies.domain.interactors.GetMovieSavedStateUseCase
import com.example.movies.domain.interactors.GetMoviesWithTypeInteractor
import com.example.tmda.presentation.movies.paging.PagingSource
import com.example.tmda.presentation.navigation.MOVIES_LIST_SCREEN_ID
import com.example.tmda.presentation.navigation.MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val pager = createPager()
    val pagesStream by lazy { getMoviesPagesStream() }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            user = userUseCase.invoke()

        }
        moviesUseCase = screenType.toUseCase()
        Log.d("xxxxx", "xxxxxx")
    }

    fun getPagerState() {
       // pager.getRefreshKey()
    }
    private fun getMoviesPagesStream(): Flow<PagingData<MovieUiDto>> =
        Pager(
            initialKey = 1,
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { pager }).flow


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

    private fun createPager(): PagingSource<MovieUiDto> {
        return PagingSource(::getMovieDtoPage)
    }

    private suspend fun getMovieDtoPage(pageIndex: Int): UiPage<MovieUiDto> {
        val moviesPage = getMoviesPagesStream(pageIndex)
        val isSavedCorrespondingList = moviesPage.results.map { getIsSaved(it.id) }
        val moviesUiDtoList = moviesPage.results.mapIndexed { index, movie ->
            makeMovieUiDto(movie, isSavedCorrespondingList[index])
        }
        return UiPage(
            page = pageIndex, results = moviesUiDtoList,
            totalPages = moviesPage.totalPages,
            totalResults = moviesPage.totalResults,
        )
    }

    private suspend fun getMoviesPagesStream(pageIndex: Int) =
        moviesUseCase.invoke(pageIndex)

    private suspend fun getIsSaved(movieId: Int) =
        getMovieSavedStateUseCase.invoke(movieId, user.sessionId)

    private fun makeMovieUiDto(movie: Movie, isSaved: Boolean) =
        MovieUiDto(movie, isSaved)

    fun addOrRemoveMovieToSavedList(movieId: Int, isSaved: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            addMovieToWatchListUseCase.invoke(16874876, user.sessionId, movieId, !isSaved)


        }
    }

}