package com.example.tmda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.bitIO.tvshowcomponent.domain.useCases.GetTopRatedTvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopRatedTvShowUseCase: GetTopRatedTvShowUseCase
) : ViewModel() {

    val uiState = MutableStateFlow("")

    init {
        viewModelScope.launch {
            uiState.emit(getTopRatedTvShowUseCase())

        }
    }


}