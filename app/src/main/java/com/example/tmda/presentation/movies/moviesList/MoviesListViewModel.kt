package com.example.tmda.presentation.movies.moviesList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.authentication.domain.interactors.GetCurrentUserUseCase
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.interactors.GetMovieSavedStateUseCase
import com.example.movies.domain.interactors.GetMoviesWithTypeInteractor
import com.example.tmda.presentation.movies.paging.MoviesPagingSource
import com.example.tmda.presentation.navigation.MOVIES_LIST_SCREEN_ID
import com.example.tmda.presentation.navigation.MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val interactor: GetMoviesWithTypeInteractor,
    private val userUseCase:GetCurrentUserUseCase,
    private val getMovieSavedStateUseCase: GetMovieSavedStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val screenType: ScreenType = savedStateHandle[MOVIES_LIST_SCREEN_ID]!!
    private val movieId: Int? = savedStateHandle[MOVIE_ID]

    private val pager = createPager()


    fun getMovieList(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { pager }).flow
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

    private fun createPager(): MoviesPagingSource {
        val useCase = screenType.toUseCase()
        return MoviesPagingSource(useCase::invoke)
    }

    private fun getMovies(){}
    private fun getIsSaved(){}
    private fun makeMovieUiDto(){}


}