package com.example.tmda.presentation.movies.movieDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.movies.domain.enities.Video
import com.example.movies.domain.enities.credits.Credits
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.enities.movie.MovieDetails
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.movies.domain.enities.review.Review
import com.example.tmda.presentation.shared.UiState
import com.example.tmda.presentation.shared.toSuccessState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsScreenStateHolder(
    val movieId: Int,
    private val coroutineScope: CoroutineScope,
    private val movieDetailsProvider: suspend (Int) -> MovieDetails,
    private val isMovieSavedProvider: suspend (Int) -> Boolean,
    private val movieVideosProvider: suspend (Int) -> List<Video>,
    private val movieCreditsProvider: suspend (Int) -> Credits,
    private val similarMoviesProvider: suspend (Int) -> MoviesPage,
    private val recommendedMoviesProvider: suspend (Int) -> MoviesPage,
    private val reviewsProvider: suspend (Int) -> List<Review>,
    private val changeSavedStateProvider: suspend (Int, Boolean) -> Unit
) {
    //
    private val _movieDetails: MutableState<UiState<MovieDetails>> =
        mutableStateOf(UiState.Loading())
    val movieDetails: State<UiState<MovieDetails>>
        get() = _movieDetails

    //
    private val _isSaved: MutableState<UiState<Boolean>> = mutableStateOf(UiState.Loading())
    val isSaved: State<UiState<Boolean>>
        get() = _isSaved

    //
    private val _movieVideos: MutableState<UiState<List<Video>>> =
        mutableStateOf(UiState.Loading())
    val movieVideos: State<UiState<List<Video>>>
        get() = _movieVideos

    //
    private val _movieCredits: MutableState<UiState<Credits>> = mutableStateOf(UiState.Loading())
    val movieCredits: State<UiState<Credits>>
        get() = _movieCredits

    //
    private val _similarMovies: MutableState<UiState<List<Movie>>> =
        mutableStateOf(UiState.Loading())
    val similarMovies: State<UiState<List<Movie>>>
        get() = _similarMovies

    //
    private val _recommendedMovies: MutableState<UiState<List<Movie>>> =
        mutableStateOf(UiState.Loading())
    val recommendedMovies: State<UiState<List<Movie>>>
        get() = _recommendedMovies

    //
    private val _reviews: MutableState<UiState<List<Review>>> =
        mutableStateOf(UiState.Loading())
    val reviews: State<UiState<List<Review>>>
        get() = _reviews
    //

    init {
        updateDetails()
        updateIsSaved()
        updateVideos()
        updateCredits()
        updateSimilarMovies()
        updateRecommendedMovies()
        updateReviews()
    }

    private fun updateDetails() =
        coroutineScope.launch(Dispatchers.IO) {
            _movieDetails.value = movieDetailsProvider(movieId).toSuccessState()
        }

    private fun updateVideos() =
        coroutineScope.launch(Dispatchers.IO) {
            _movieVideos.value = movieVideosProvider(movieId).toSuccessState()
        }

    fun updateIsSaved() =
        coroutineScope.launch(Dispatchers.IO) {
            _isSaved.value = isMovieSavedProvider(movieId).toSuccessState()
        }

    private fun updateCredits() {
        coroutineScope.launch(Dispatchers.IO) {
            _movieCredits.value = movieCreditsProvider(movieId).toSuccessState()
        }
    }

    private fun updateSimilarMovies() {
        coroutineScope.launch(Dispatchers.IO) {

            _similarMovies.value = similarMoviesProvider(1).results.toSuccessState()
        }
    }

    private fun updateRecommendedMovies() {
        coroutineScope.launch(Dispatchers.IO) {
            _recommendedMovies.value = recommendedMoviesProvider(1).results.toSuccessState()
        }
    }

    private fun updateReviews() {
        coroutineScope.launch(Dispatchers.IO) {
            _reviews.value = reviewsProvider(movieId).toSuccessState()
        }
    }

    fun addOrRemoveMovieToSavedList() {
        coroutineScope.launch(Dispatchers.IO) {
            when (val isSaved = _isSaved.value) {
                is UiState.Failure -> {}
                is UiState.Loading -> {}
                is UiState.Success -> {
                    changeSavedStateProvider(movieId, isSaved.data)
                    _isSaved.value = (!isSaved.data).toSuccessState()
                }
            }


        }
    }

}