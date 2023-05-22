package com.bitIO.ui.screens.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.useCases.GetTopRatedTvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val getTopRatedTvShow: GetTopRatedTvShowUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TvShowTopRatedState())
    val uiState = _uiState.asStateFlow()

     val _uiState2 = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _uiState2.update { "From ViewModel" }
//            _uiState.update { it.copy(result = getTopRatedTvShow()) }
        }
    }

}