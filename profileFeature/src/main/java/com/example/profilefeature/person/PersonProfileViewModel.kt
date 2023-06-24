package com.example.profilefeature.person

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.useCases.GetPersonDetailsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetPersonSeriesUseCase
import com.example.moviesComponent.domain.enities.movie.Movie
import com.example.moviesComponent.domain.useCases.GetPersonMoviesUseCase
import com.example.sharedComponent.entities.people.PersonDetails
import com.example.sharedui.navigation.PERSON_ID
import com.example.sharedui.uiStates.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonProfileViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getPersonMoviesUseCase: GetPersonMoviesUseCase,
    private val getPersonSeriesUseCase: GetPersonSeriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val personId: Int = savedStateHandle[PERSON_ID]!!
    private val _personDetailsDetails: MutableState<com.example.sharedui.uiStates.UiState<PersonDetails>> =
        mutableStateOf(com.example.sharedui.uiStates.UiState.Loading())
    val personDetails: State<com.example.sharedui.uiStates.UiState<PersonDetails>>
        get() = _personDetailsDetails
    private val _personMovies: MutableState<com.example.sharedui.uiStates.UiState<List<Movie>>> =
        mutableStateOf(com.example.sharedui.uiStates.UiState.Loading())
    val personMovies: State<com.example.sharedui.uiStates.UiState<List<Movie>>>
        get() = _personMovies
    private val _personSeries: MutableState<com.example.sharedui.uiStates.UiState<List<TvShow>>> =
        mutableStateOf(com.example.sharedui.uiStates.UiState.Loading())
    val personSeries: State<com.example.sharedui.uiStates.UiState<List<TvShow>>>
        get() = _personSeries

    init {
        updateAll()
    }
    private fun updateAll() {
        updatePersonDetails()
        updatePersonMovies()
        updatePersonSeries()
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

    private fun updatePersonSeries() {
        viewModelScope.launch {
            _personSeries.value = getPersonSeriesUseCase.invoke(personId).toUiState()
        }
    }
}