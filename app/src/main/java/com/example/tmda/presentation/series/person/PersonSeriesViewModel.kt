package com.example.tmda.presentation.series.person

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.useCases.GetPersonDetailsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetPersonSeriesUseCase
import com.example.shared.entities.people.PersonDetails
import com.example.tmda.presentation.navigation.PERSON_ID
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.presentation.shared.uiStates.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonSeriesViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getPersonSeriesUseCase: GetPersonSeriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val personId: Int = savedStateHandle[PERSON_ID]!!
    private val _personDetailsDetails: MutableState<UiState<PersonDetails>> = mutableStateOf(UiState.Loading())
    val personDetails: State<UiState<PersonDetails>>
        get() = _personDetailsDetails
    private val _personSeries: MutableState<UiState<List<TvShow>>> =
        mutableStateOf(UiState.Loading())
    val personSeries: State<UiState<List<TvShow>>>
        get() = _personSeries

    init {
        updateAll()
    }

    fun updateAll() {
        updatePersonDetails()
        updatePersonSeries()
    }

    fun updatePersonDetails() {
        viewModelScope.launch {
            _personDetailsDetails.value = getPersonDetailsUseCase.invoke(personId).toUiState()
        }
    }

    fun updatePersonSeries() {
        viewModelScope.launch {
            _personSeries.value = getPersonSeriesUseCase.invoke(personId).toUiState()
        }
    }
}