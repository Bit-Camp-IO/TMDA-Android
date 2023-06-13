package com.example.tmda.presentation.movies.moviesHome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.interactors.GetMoviesWithTypeInteractor
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesHomeViewModel @Inject constructor(private val interactor: GetMoviesWithTypeInteractor) :
    ViewModel() {
    init {
        getNowPlayingMovies()
        getUpComingMovies()
        getUpTopRatedMovies()
        getUpPopularMovies()
    }

    private val _nowPlayingMoviesState = mutableStateOf<List<Movie>>(listOf())
    val nowPlayingMoviesState: State<List<Movie>>
        get() = _nowPlayingMoviesState

    private val _upComingMoviesState = mutableStateOf<List<MovieUiDto>>(listOf())
    val upComingMoviesState: State<List<MovieUiDto>>
        get() = _upComingMoviesState

    private val _topRatedMoviesState = mutableStateOf<List<MovieUiDto>>(listOf())
    val topRatedMoviesState: State<List<MovieUiDto>>
        get() = _topRatedMoviesState

    private val _popularMoviesState = mutableStateOf<List<MovieUiDto>>(listOf())
    val popularMoviesState: State<List<MovieUiDto>>
        get() = _popularMoviesState

    private fun getNowPlayingMovies() =
        viewModelScope.launch(Dispatchers.IO) {
            val movies = interactor.invoke(GetMoviesWithTypeInteractor.MovieType.NowPlaying)
                .invoke(1).results.take(5)
            _nowPlayingMoviesState.value = movies
        }


    private fun getUpComingMovies() =
        viewModelScope.launch(Dispatchers.IO) {
            val movies =
                interactor.invoke(GetMoviesWithTypeInteractor.MovieType.Upcoming)
                    .invoke(1).results.map { MovieUiDto(it, false) }
            _upComingMoviesState.value = movies
        }

    private fun getUpTopRatedMovies() =
        viewModelScope.launch(Dispatchers.IO) {
            val movies =
                interactor.invoke(GetMoviesWithTypeInteractor.MovieType.TopRated).invoke(1).results
            _topRatedMoviesState.value = movies.map { MovieUiDto(it, false) }
        }

    private fun getUpPopularMovies() =
        viewModelScope.launch(Dispatchers.IO) {
            val movies =
                interactor.invoke(GetMoviesWithTypeInteractor.MovieType.Popular).invoke(1).results
            _popularMoviesState.value = movies.map { MovieUiDto(it, false) }
        }


}