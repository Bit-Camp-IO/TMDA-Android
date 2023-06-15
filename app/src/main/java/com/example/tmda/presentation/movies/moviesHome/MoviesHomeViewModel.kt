package com.example.tmda.presentation.movies.moviesHome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.useCases.MovieUseCaseFactory
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import com.example.tmda.presentation.shared.UiState
import com.example.tmda.presentation.shared.mapToOtherType
import com.example.tmda.presentation.shared.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesHomeViewModel @Inject constructor(private val interactor: MovieUseCaseFactory) :
    ViewModel() {
    init {
      updateAll()
    }

    private val _nowPlayingMoviesState = mutableStateOf<UiState<List<Movie>>>(UiState.Loading())
    val nowPlayingMoviesState: State<UiState<List<Movie>>>
        get() = _nowPlayingMoviesState

    private val _upComingMoviesState = mutableStateOf<UiState<List<MovieUiDto>>>(UiState.Loading())
    val upComingMoviesState: State<UiState<List<MovieUiDto>>>
        get() = _upComingMoviesState

    private val _topRatedMoviesState = mutableStateOf<UiState<List<MovieUiDto>>>(UiState.Loading())
    val topRatedMoviesState: State<UiState<List<MovieUiDto>>>
        get() = _topRatedMoviesState

    private val _popularMoviesState = mutableStateOf<UiState<List<MovieUiDto>>>(UiState.Loading())
    val popularMoviesState: State<UiState<List<MovieUiDto>>>
        get() = _popularMoviesState


    fun updateAll(){
        updateNowPlayingMovies()
        updateUpComingMovies()
        updateTopRatedMovies()
        updatePopularMovies()
    }

    private fun updateNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = interactor.getUseCase(MovieUseCaseFactory.MovieType.NowPlaying)
                .invoke(1).mapToOtherType { it.results.take(5) }.toUiState()
            _nowPlayingMoviesState.value = movies

        }
    }

    private fun updateUpComingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val moviesUiState =
                interactor.getUseCase(MovieUseCaseFactory.MovieType.Upcoming)
                    .invoke(1).mapToOtherType { it -> it.results.map { MovieUiDto(it, false) } }
                    .toUiState()

            _upComingMoviesState.value = moviesUiState
        }
    }

    private fun updateTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val moviesUiState =
                interactor.getUseCase(MovieUseCaseFactory.MovieType.TopRated).invoke(1)
                    .mapToOtherType { it -> it.results.map { MovieUiDto(it, false) } }
                    .toUiState()
            _topRatedMoviesState.value = moviesUiState
        }
    }

    private fun updatePopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val moviesUiState =
                interactor.getUseCase(MovieUseCaseFactory.MovieType.Popular).invoke(1)
                    .mapToOtherType { it -> it.results.map { MovieUiDto(it, false) } }
                    .toUiState()
            _popularMoviesState.value = moviesUiState
        }
    }


    fun isErrorState(): Boolean {
        return _nowPlayingMoviesState.value is UiState.Failure &&
                _upComingMoviesState.value is UiState.Failure &&
                _topRatedMoviesState.value is UiState.Failure &&
                _popularMoviesState.value is UiState.Failure

    }
}