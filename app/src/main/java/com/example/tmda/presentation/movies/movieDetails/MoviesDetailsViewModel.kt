package com.example.tmda.presentation.movies.movieDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.domain.entities.User
import com.example.authentication.domain.interactors.GetCurrentUserUseCase
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.interactors.AddMovieToWatchListUseCase
import com.example.movies.domain.interactors.GetMovieCreditsUseCase
import com.example.movies.domain.interactors.GetMovieDetailsInteractor
import com.example.movies.domain.interactors.GetMovieReviewsUseCase
import com.example.movies.domain.interactors.GetMovieSavedStateUseCase
import com.example.movies.domain.interactors.GetMovieVideosUseCase
import com.example.movies.domain.interactors.GetMoviesWithTypeInteractor
import com.example.tmda.presentation.navigation.MOVIE_ID
import com.example.tmda.presentation.shared.UiState
import com.example.tmda.presentation.shared.toSuccessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesDetailsViewModel @Inject constructor(
    private val detailsUseCase: GetMovieDetailsInteractor,
    private val creditsUseCase: GetMovieCreditsUseCase,
    private val movieUseCase: GetMoviesWithTypeInteractor,
    private val reviewsUseCase: GetMovieReviewsUseCase,
    private val videosUseCase: GetMovieVideosUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getMovieSavedStateUseCase: GetMovieSavedStateUseCase,
    private val addMovieToWatchListUseCase: AddMovieToWatchListUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val movieId: Int = savedStateHandle[MOVIE_ID]!!

    private val _movieScreenDetails: MutableState<UiState<MovieDetailsScreenDto>> =
        mutableStateOf(UiState.Loading())
    val movieScreenDetails: State<UiState<MovieDetailsScreenDto>>
        get() = _movieScreenDetails
    private val _isSaved = mutableStateOf(false)
    val isSaved: State<Boolean>
        get() = _isSaved

    private lateinit var user: User

    init {
        _movieScreenDetails.value = UiState.Loading()
        viewModelScope.launch { user = getCurrentUserUseCase.invoke() }
        getMovieDetails()
    }

    private suspend fun invokeGetMovieUseCase(
        useCaseType: GetMoviesWithTypeInteractor.MovieTypeWithId
    ): Deferred<List<Movie>> = coroutineScope {
        async {
            movieUseCase.invoke(
                useCaseType, movieId
            ).invoke(1).results
        }
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            val details = async { detailsUseCase.invoke(movieId) }
            val credits = async { creditsUseCase.invoke(movieId) }
            val similarMovies =
                invokeGetMovieUseCase(GetMoviesWithTypeInteractor.MovieTypeWithId.Similar)
            val recommendedMovies =
                invokeGetMovieUseCase(GetMoviesWithTypeInteractor.MovieTypeWithId.Recommended)
            val reviews = async { reviewsUseCase.invoke(movieId) }
            val videos = async { videosUseCase.invoke(movieId) }
            val isSaved = async { getMovieSavedStateUseCase.invoke(movieId, user.sessionId) }

            val movieDetailsScreenDto =
                MovieDetailsScreenDto(
                    details.await(),
                    credits.await(),
                    similarMovies.await(),
                    recommendedMovies.await(),
                    reviews.await(),
                    videos.await(),
                    isSaved.await()
                ).toSuccessState()
            _movieScreenDetails.value = movieDetailsScreenDto
            _isSaved.value = movieDetailsScreenDto.data.isSaved
        }
    }

    fun addMovieToSavedList() {
        viewModelScope.launch(Dispatchers.IO) {
            addMovieToWatchListUseCase.invoke(16874876, user.sessionId, movieId, !_isSaved.value)
            _isSaved.value = !_isSaved.value
        }


    }
}

