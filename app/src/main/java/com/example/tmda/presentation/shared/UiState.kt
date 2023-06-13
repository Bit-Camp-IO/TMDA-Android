package com.example.tmda.presentation.shared

sealed interface UiState<T : Any> {
    operator fun <O:Any> plus(uiState: UiState<O>)= listOf(this, uiState)


    data class Success<T : Any>(val data: T) : UiState<T>
    data class Failure<T : Any>(val errorMassage: String) : UiState<T>
    class Loading<T : Any> : UiState<T>


}

fun <T : Any> T.toSuccessState() = UiState.Success(this)