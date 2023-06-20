package com.example.tmda.presentation.movies.movieDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.enities.movie.MovieDetails
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.shared.entities.Video
import com.example.shared.entities.credits.CastMember
import com.example.shared.entities.credits.Credits
import com.example.shared.entities.review.Review
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.presentation.shared.uiStates.mapToOtherType
import com.example.tmda.presentation.shared.uiStates.toSuccessState
import com.example.tmda.presentation.shared.uiStates.toUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class DetailsScreenStateHolder(
    val movieId: Int,
    private val coroutineScope: CoroutineScope,
    private val movieDetailsProvider: suspend (Int) -> Result<MovieDetails>,
    private val isMovieSavedProvider: suspend (Int) -> Result<Boolean>,
    private val movieVideosProvider: suspend (Int) -> Result<List<Video>>,
    private val movieCreditsProvider: suspend (Int) -> Result<Credits>,
    private val similarMoviesProvider: suspend (Int) -> Result<MoviesPage>,
    private val recommendedMoviesProvider: suspend (Int) -> Result<MoviesPage>,
    private val reviewsProvider: suspend (Int) -> Result<List<Review>>,
    private val changeSavedStateProvider: suspend (Int, Boolean) -> Result<Unit>
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
    private val _movieCredits: MutableState<UiState<List<CastMember>>> = mutableStateOf(UiState.Loading())
    val movieCredits: State<UiState<List<CastMember>>>
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

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean>
        get() = _isError
    //


    init {
        updateAll()
    }

    fun updateAll() {
        coroutineScope.launch {
            listOf(
                updateDetails(),
                updateIsSaved(),
                updateVideos(),
                updateCredits(),
                updateSimilarMovies(),
                updateRecommendedMovies(),
                updateReviews()
            ).awaitAll()
            _isError.value = getIsErrorState()
        }
    }

    private fun updateDetails() =
        coroutineScope.async(Dispatchers.IO) {
            _movieDetails.value = movieDetailsProvider(movieId).toUiState()

        }

    private fun updateVideos() =
        coroutineScope.async(Dispatchers.IO) {
            _movieVideos.value =
                movieVideosProvider(movieId).toUiState()

        }

    fun updateIsSaved() =
        coroutineScope.async(Dispatchers.IO) {
            _isSaved.value = isMovieSavedProvider(movieId).toUiState()


        }

    private fun updateCredits() = coroutineScope.async(Dispatchers.IO) {
        _movieCredits.value = movieCreditsProvider(movieId).mapToOtherType { it.cast } .toUiState()
    }


    private fun updateSimilarMovies() =
        coroutineScope.async(Dispatchers.IO) {
            _similarMovies.value =
                similarMoviesProvider(1).mapToOtherType { it.results }.toUiState()
        }

    private fun updateRecommendedMovies() =
        coroutineScope.async(Dispatchers.IO) {
            _recommendedMovies.value =
                recommendedMoviesProvider(1).mapToOtherType { it.results }.toUiState()

        }


    private fun updateReviews() =
        coroutineScope.async(Dispatchers.IO) {
            _reviews.value = reviewsProvider(movieId).toUiState()
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

    private fun getIsErrorState(): Boolean {
        return listOf(
            _reviews,
            _recommendedMovies,
            _isSaved,
            _similarMovies,
            _movieDetails,
            _movieVideos,
            _movieCredits
        ).any { it.value is UiState.Failure }
    }

}