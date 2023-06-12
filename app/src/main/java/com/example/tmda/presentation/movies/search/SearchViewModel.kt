@file:OptIn(FlowPreview::class)

package com.example.tmda.presentation.movies.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.interactors.SearchMoviesUseCase
import com.example.tmda.presentation.movies.paging.MoviePageProvider
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

    private val moviePageProvider = MoviePageProvider(viewModelScope, searchMoviesUseCase::invoke)
    var pageStream: Flow<PagingData<Movie>> =
        moviePageProvider.currentPager.flow.cachedIn(viewModelScope)
        private set
    private val movieActionsStream = MutableSharedFlow<String>()
     private val actionStreamDebounced=  movieActionsStream.debounce(1000)
    val currentKeyword = mutableStateOf("")

    init {
        observeKeyword()
    }


    fun updateKeyword(keyword: String) {
        currentKeyword.value=keyword
        viewModelScope.launch {
            movieActionsStream.emit(keyword)
        }
    }

    private fun observeKeyword(){
        viewModelScope.launch(Dispatchers.Unconfined) {
            actionStreamDebounced.collect{
                moviePageProvider.changeKeyword(it)
            }
        }

    }


}