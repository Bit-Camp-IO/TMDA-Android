package com.example.moviesfeature.person

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesComponent.domain.enities.movie.Movie
import com.example.moviesComponent.domain.useCases.GetPersonDetailsUseCase
import com.example.moviesComponent.domain.useCases.GetPersonMoviesUseCase
import com.example.sharedComponent.entities.people.PersonDetails
import com.example.sharedui.navigation.PERSON_ID
import com.example.sharedui.uiStates.UiState
import com.example.sharedui.uiStates.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonMovieViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getPersonMoviesUseCase: GetPersonMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val personId: Int = savedStateHandle[PERSON_ID]!!
    private val _personDetailsDetails: MutableState<UiState<PersonDetails>> = mutableStateOf(UiState.Loading())
    val personDetails: State<UiState<PersonDetails>>
        get() = _personDetailsDetails
    private val _personMovies: MutableState<UiState<List<Movie>>> =
        mutableStateOf(UiState.Loading())
    val personMovies: State<UiState<List<Movie>>>
        get() = _personMovies

    init {
        updateAll()
    }

    fun updateAll() {
        updatePersonDetails()
        updatePersonMovies()
    }

    private fun updatePersonDetails() {
        viewModelScope.launch {
            _personDetailsDetails.value = getPersonDetailsUseCase.invoke(personId).toUiState()
        }
    }

    private fun updatePersonMovies() {
        viewModelScope.launch {
            _personMovies.value = getPersonMoviesUseCase.invoke(personId).toUiState()
        }
    }
}