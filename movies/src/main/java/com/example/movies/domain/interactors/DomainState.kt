package com.example.movies.domain.interactors


sealed interface DomainState<T : Any> {
    data class Success<T : Any>(val data: T) : DomainState<T>
    data class Error<T : Any>(val message: String, val data: T? = null) : DomainState<T>


}

fun <T : Any> T.wrapWithSuccess() = DomainState.Success(this)
fun <T : Any> String?.wrapWithError(data: T? = null) =
    DomainState.Error(this ?: DEFAULT_ERROR_MESSAGE, data)

const val DEFAULT_ERROR_MESSAGE = "Couldn't reach server. Check your internet connection."