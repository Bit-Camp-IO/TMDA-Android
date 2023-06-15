@file:OptIn(FlowPreview::class, FlowPreview::class)

package com.example.tmda.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.movies.domain.useCases.SearchMoviesUseCase
import com.example.tmda.presentation.movies.paging.MoviePagingProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchMoviesUseCase: SearchMoviesUseCase) :
    ViewModel() {
    private val _searchType = mutableStateOf(SearchType.Movie)
    val searchType: State<SearchType>
        get() = _searchType


    private val moviePagingProvider = MoviePagingProvider(viewModelScope, ::moviePageProvider)
    var pageStream: Flow<PagingData<Movie>> =
        moviePagingProvider.currentPager.flow.cachedIn(viewModelScope)
        private set


    private val movieActionsStream = MutableSharedFlow<String>()
    private val actionStreamDebounced = movieActionsStream.debounce(700)
    val currentKeyword = mutableStateOf("")

    init {
        observeKeyword()
    }

    private suspend fun moviePageProvider(keyword: String, page: Int): MoviesPage {
        val result = searchMoviesUseCase.invoke(keyword, page)
        return if (result.isSuccess) {
            result.getOrNull()!!
        } else {
            MoviesPage(
                page,
                results = listOf(),
                totalPages = Int.MAX_VALUE,
                totalResults = Int.MAX_VALUE
            )
        }
    }

    fun updateKeyword(keyword: String) {
        currentKeyword.value = keyword
        viewModelScope.launch {
            movieActionsStream.emit(keyword)
        }
    }

    private fun observeKeyword() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            actionStreamDebounced.collect {
                moviePagingProvider.changeKeyword(it)
            }
        }

    }

    fun changeSearchType(type: SearchType) {
        if (_searchType.value == type) return
        _searchType.value = type
    }


}

enum class SearchType(val title: String) {
    Movie("Movies"),
    Series("Tv Shows"),
    Actors("Actors")
}