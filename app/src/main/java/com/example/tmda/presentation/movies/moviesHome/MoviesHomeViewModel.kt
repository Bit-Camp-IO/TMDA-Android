package com.example.tmda.presentation.movies.moviesHome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.interactors.GetMoviesWithTypeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _upComingMoviesState = mutableStateOf<List<Movie>>(listOf())
    val upComingMoviesState: State<List<Movie>>
        get() = _upComingMoviesState

    private val _topRatedMoviesState = mutableStateOf<List<Movie>>(listOf())
    val topRatedMoviesState: State<List<Movie>>
        get() = _topRatedMoviesState

    private val _popularMoviesState = mutableStateOf<List<Movie>>(listOf())
    val popularMoviesState: State<List<Movie>>
        get() = _popularMoviesState

    fun getNowPlayingMovies() =
        viewModelScope.launch {
            val movies = interactor.invoke(GetMoviesWithTypeInteractor.MovieType.NowPlaying)
                .invoke(1).results.take(5)
            _nowPlayingMoviesState.value = movies
        }


    fun getUpComingMovies() =
        viewModelScope.launch {
            val movies =
                interactor.invoke(GetMoviesWithTypeInteractor.MovieType.Upcoming).invoke(1).results
            _upComingMoviesState.value = movies
        }

    fun getUpTopRatedMovies() =
        viewModelScope.launch {
            val movies =
                interactor.invoke(GetMoviesWithTypeInteractor.MovieType.TopRated).invoke(1).results
            _topRatedMoviesState.value = movies
        }

    fun getUpPopularMovies() =
        viewModelScope.launch {
            val movies =
                interactor.invoke(GetMoviesWithTypeInteractor.MovieType.Popular).invoke(1).results
            _popularMoviesState.value = movies
        }


}