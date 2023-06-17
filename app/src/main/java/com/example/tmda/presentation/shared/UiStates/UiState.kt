package com.example.tmda.presentation.shared.UiStates

sealed interface UiState<T : Any> {
    operator fun <O : Any> plus(uiState: UiState<O>) = listOf(this, uiState)
    data class Success<T : Any>(val data: T) : UiState<T>
    data class Failure<T : Any>(val errorMassage: String) : UiState<T>
    class Loading<T : Any> : UiState<T>


}

fun <T : Any> T.toSuccessState() = UiState.Success(this)
fun <T : Any> Result<T>.toUiState(): UiState<T> {
    var uiState: UiState<T>? = null
    onSuccess { uiState = it.toSuccessState() }
    onFailure { uiState = UiState.Failure(it.message ?: "") }
    return uiState!!

}

fun <T : Any, O : Any> Result<T>.mapToOtherType(mapOnSuccess: (T) -> O): Result<O> {

    var result: Result<O>? = null
    onSuccess { result =Result.success(mapOnSuccess(it)) }
    onFailure { result = Result.failure(it) }
    return result!!
}
