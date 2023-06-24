package com.example.moviesfeature.movieDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.moviesComponent.domain.enities.movie.Movie
import com.example.moviesComponent.domain.enities.movie.MovieDetails
import com.example.moviesComponent.domain.enities.movie.MoviesPage
import com.example.sharedComponent.entities.Video
import com.example.sharedComponent.entities.credits.CastMember
import com.example.sharedComponent.entities.credits.Credits
import com.example.sharedComponent.entities.review.Review
import com.example.sharedui.uiStates.UiState
import com.example.sharedui.uiStates.mapToOtherType
import com.example.sharedui.uiStates.toUiState

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private val _movieCredits: MutableState<UiState<List<CastMember>>> =
        mutableStateOf(UiState.Loading())
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
            val state = movieDetailsProvider(movieId).toUiState()
            withContext(Dispatchers.Main) { _movieDetails.value = state }

        }

    private fun updateVideos() =
        coroutineScope.async(Dispatchers.IO) {
            val uiState = movieVideosProvider(movieId).toUiState()
            withContext(Dispatchers.Main) { _movieVideos.value = uiState }


        }

    fun updateIsSaved() =
        coroutineScope.async(Dispatchers.IO) {
            val state = isMovieSavedProvider(movieId).toUiState()
            withContext(Dispatchers.Main) { _isSaved.value = state }


        }

    private fun updateCredits() = coroutineScope.async(Dispatchers.IO) {
        val state = movieCreditsProvider(movieId).mapToOtherType { it.cast }.toUiState()
        withContext(Dispatchers.Main) { _movieCredits.value = state }

    }


    private fun updateSimilarMovies() =
        coroutineScope.async(Dispatchers.IO) {
            val state = similarMoviesProvider(1).mapToOtherType { it.results }.toUiState()
            withContext(Dispatchers.Main) { _similarMovies.value = state }


        }

    private fun updateRecommendedMovies() =
        coroutineScope.async(Dispatchers.IO) {
            val state = recommendedMoviesProvider(1).mapToOtherType { it.results }.toUiState()
            withContext(Dispatchers.Main) { _recommendedMovies.value = state }


        }


    private fun updateReviews() =
        coroutineScope.async(Dispatchers.IO) {
            val state = reviewsProvider(movieId).toUiState()
            withContext(Dispatchers.Main) { _reviews.value = state }

        }

    fun addOrRemoveMovieToSavedList() {
        coroutineScope.launch(Dispatchers.IO) {
            when (val isSaved = _isSaved.value) {
                is UiState.Failure -> updateIsSaved()
                is UiState.Loading -> {}
                is UiState.Success -> {
                    withContext(Dispatchers.Main) { _isSaved.value = UiState.Loading() }
                    changeSavedStateProvider(movieId, isSaved.data)
                    updateIsSaved()
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