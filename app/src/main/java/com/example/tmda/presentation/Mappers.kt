package com.example.tmda.presentation

import com.example.movies.domain.interactors.DomainState
import com.example.tmda.presentation.shared.UiState

fun <T : Any> DomainState<T>.toUiState() = when (this) {
    is DomainState.Error -> UiState.Failure(this.message)
    is DomainState.Success -> UiState.Success(this.data)
}
