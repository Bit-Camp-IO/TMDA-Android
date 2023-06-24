package com.example.moviesfeature.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesComponent.domain.useCases.AddMovieToWatchListUseCase
import com.example.moviesComponent.domain.useCases.GetMovieCreditsUseCase
import com.example.moviesComponent.domain.useCases.GetMovieDetailsInteractor
import com.example.moviesComponent.domain.useCases.GetMovieReviewsUseCase
import com.example.moviesComponent.domain.useCases.GetMovieSavedStateUseCase
import com.example.moviesComponent.domain.useCases.GetMovieVideosUseCase
import com.example.moviesComponent.domain.useCases.MovieUseCaseFactory
import com.example.moviesfeature.navigation.MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: GetMovieDetailsInteractor,
    private val creditsUseCase: GetMovieCreditsUseCase,
    private val movieUseCase: MovieUseCaseFactory,
    private val reviewsUseCase: GetMovieReviewsUseCase,
    private val videosUseCase: GetMovieVideosUseCase,
    private val getMovieSavedStateUseCase: GetMovieSavedStateUseCase,
    private val addMovieToWatchListUseCase: AddMovieToWatchListUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val movieId: Int = savedStateHandle[MOVIE_ID]!!
    lateinit var detailsScreenStateHolder: DetailsScreenStateHolder
        private set
    init {
        viewModelScope.launch {
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
        similarMoviesProvider = invokeGetMovieUseCase(MovieUseCaseFactory.MovieTypeWithId.Similar)::invoke,
        recommendedMoviesProvider = invokeGetMovieUseCase(useCaseType = MovieUseCaseFactory.MovieTypeWithId.Recommended)::invoke,
        reviewsProvider = reviewsUseCase::invoke,
        changeSavedStateProvider = ::addOrRemoveToSaveListProvider
    )

    private fun invokeGetMovieUseCase(useCaseType: MovieUseCaseFactory.MovieTypeWithId):
            MovieUseCaseFactory.MovieUseCaseWithId =
        movieUseCase.getUseCase(useCaseType, movieId)

    private suspend fun isMovieSavedProvider(movieId: Int):Result< Boolean> {
        return getMovieSavedStateUseCase.invoke(movieId)
    }

    private suspend fun addOrRemoveToSaveListProvider(movieId: Int, currentSavedValue: Boolean) =
        addMovieToWatchListUseCase.invoke( movieId, !currentSavedValue)

    fun addOrRemoveMovieToSavedList() =
        detailsScreenStateHolder.addOrRemoveMovieToSavedList()

    fun checkIfSavedStateUpdated() {
        detailsScreenStateHolder.updateIsSaved()
    }
}