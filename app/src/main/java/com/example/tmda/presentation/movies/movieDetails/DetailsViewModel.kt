package com.example.tmda.presentation.movies.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.domain.entities.User
import com.example.authentication.domain.interactors.GetCurrentUserUseCase
import com.example.movies.domain.interactors.AddMovieToWatchListUseCase
import com.example.movies.domain.interactors.GetMovieCreditsUseCase
import com.example.movies.domain.interactors.GetMovieDetailsInteractor
import com.example.movies.domain.interactors.GetMovieReviewsUseCase
import com.example.movies.domain.interactors.GetMovieSavedStateUseCase
import com.example.movies.domain.interactors.GetMovieVideosUseCase
import com.example.movies.domain.interactors.GetMoviesWithTypeInteractor
import com.example.tmda.presentation.navigation.MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
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
    private lateinit var user: User
    lateinit var detailsScreenStateHolder: DetailsScreenStateHolder
        private set

    init {
        viewModelScope.launch {
            user = getCurrentUserUseCase.invoke()
            detailsScreenStateHolder = getStateHolder()
        }
    }



    private fun getStateHolder() = DetailsScreenStateHolder(
        movieId = movieId,
        coroutineScope = viewModelScope,
        movieDetailsProvider = detailsUseCase::invoke,
        isMovieSavedProvider = ::isMovieSavedProvider,
        movieVideosProvider = videosUseCase::invoke,
        movieCreditsProvider = creditsUseCase::invoke,
        similarMoviesProvider = invokeGetMovieUseCase(GetMoviesWithTypeInteractor.MovieTypeWithId.Similar)::invoke,
        recommendedMoviesProvider = invokeGetMovieUseCase(useCaseType = GetMoviesWithTypeInteractor.MovieTypeWithId.Recommended)::invoke,
        reviewsProvider = reviewsUseCase::invoke,
        changeSavedStateProvider = ::addOrRemoveToSaveListProvider
    )

    private fun invokeGetMovieUseCase(useCaseType: GetMoviesWithTypeInteractor.MovieTypeWithId):
            GetMoviesWithTypeInteractor.MovieUseCaseWithId =
        movieUseCase.invoke(useCaseType, movieId)

    private suspend fun isMovieSavedProvider(movieId: Int): Boolean {
        return getMovieSavedStateUseCase.invoke(movieId, user.sessionId)
    }

    private suspend fun addOrRemoveToSaveListProvider(movieId: Int, currentSavedValue: Boolean) =
        addMovieToWatchListUseCase.invoke(16874876, user.sessionId, movieId, !currentSavedValue)

    fun addOrRemoveMovieToSavedList() =
        detailsScreenStateHolder.addOrRemoveMovieToSavedList()

    fun checkIfSavedStateUpdated() {
        detailsScreenStateHolder.updateIsSaved()
    }
}