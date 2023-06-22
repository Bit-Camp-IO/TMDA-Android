package com.example.tmda.presentation.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.useCases.GetBookMarkedSeriesUseCase
import com.example.authentication.domain.entities.UserDetails
import com.example.authentication.domain.useCases.GetUserDetailsUseCase
import com.example.authentication.domain.useCases.SignOutUseCase
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.useCases.GetBookMarkedMoviesUseCase
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.presentation.shared.uiStates.mapToOtherType
import com.example.tmda.presentation.shared.uiStates.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getBookMarkedMoviesUseCase: GetBookMarkedMoviesUseCase,
    private val getBookMarkedSeriesUseCase: GetBookMarkedSeriesUseCase,
    private val signOutUseCase: SignOutUseCase,

    ) : ViewModel() {
    private val _userDetails: MutableState<UiState<UserDetails>> = mutableStateOf(UiState.Loading())
    val userDetails: State<UiState<UserDetails>>
        get() = _userDetails

    private val _userMovies: MutableState<UiState<List<Movie>>> = mutableStateOf(UiState.Loading())
    val userMovies: State<UiState<List<Movie>>>
        get() = _userMovies
    private val _userSeries: MutableState<UiState<List<TvShow>>> = mutableStateOf(UiState.Loading())
    val userSeries: State<UiState<List<TvShow>>>
        get() = _userSeries




    init {

        updateAll()
    }

    fun updateAll() {
        updateUserDetails()
        updateUserMovies()
        updateUserSeries()

    }

    private fun updateUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = getUserDetailsUseCase.invoke().toUiState()
            withContext(Dispatchers.Main) {
                _userDetails.value = state
            }

        }
    }

    private fun updateUserMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val state =
                getBookMarkedMoviesUseCase.invoke(1).mapToOtherType { it.results }.toUiState()
            withContext(Dispatchers.Main) {
                _userMovies.value = state
            }


        }
    }

    private fun updateUserSeries() {
        viewModelScope.launch(Dispatchers.IO) {
            val state =
                getBookMarkedSeriesUseCase.invoke(1).mapToOtherType { it.results }.toUiState()
            withContext(Dispatchers.Main) {
                _userSeries.value = state
            }


        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            signOutUseCase.invoke()
        }
    }

}