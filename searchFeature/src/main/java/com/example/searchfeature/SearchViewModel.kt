package com.example.searchfeature

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.useCases.SearchSeriesUseCase

import com.example.movies.domain.useCases.SearchMoviesUseCase
import com.example.movies.domain.useCases.SearchPeopleUseCase
import com.example.shared.entities.people.Person
import com.example.searchfeature.data.SearchItemModel
import com.example.searchfeature.data.toSearchItem

import com.example.sharedui.uiStates.mapToOtherType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchSeriesUseCase: SearchSeriesUseCase,
    private val searchPeopleUseCase: SearchPeopleUseCase
) : ViewModel() {
    private val _searchType = mutableStateOf(SearchType.Movie)
    val searchType: State<SearchType>
        get() = _searchType
    //
    val moviesStateHolder = SearchStateHolder(viewModelScope, ::moviePageProvider)
    val seriesStateHolder = SearchStateHolder(viewModelScope, ::seriesPageProvider)
    val actorStateHolder = SearchStateHolder(viewModelScope, ::peoplePageProvider)
    var currentStateHolder: SearchStateHolder<out Any> = moviesStateHolder

    //


    private suspend fun moviePageProvider(
        keyword: String,
        page: Int
    ): Result<com.example.sharedui.paging.UiPage<SearchItemModel>> {
        return searchMoviesUseCase.invoke(keyword, page).mapToOtherType { it ->
            com.example.sharedui.paging.UiPage(
                page = it.page,
                results = it.results.map { it.toSearchItem() },
                totalPages = it.totalPages
            )
        }
    }

    private suspend fun seriesPageProvider(
        keyword: String,
        page: Int
    ): Result<com.example.sharedui.paging.UiPage<SearchItemModel>> {
        return searchSeriesUseCase.invoke(keyword, page).mapToOtherType { tvShowPage ->
            com.example.sharedui.paging.UiPage(
                page = tvShowPage.page,
                results = tvShowPage.results.map { it.toSearchItem() },
                totalPages = tvShowPage.totalPages
            )
        }
    }

    private suspend fun peoplePageProvider(
        keyword: String,
        page: Int
    ): Result<com.example.sharedui.paging.UiPage<Person>> {
        return searchPeopleUseCase.invoke(keyword, page).mapToOtherType {
            com.example.sharedui.paging.UiPage(
                page = it.page,
                results = it.results,
                totalPages = it.totalPages,
            )
        }
    }


    fun changeSearchType(type: SearchType) {
        if (_searchType.value == type) return
        _searchType.value = type
        currentStateHolder = when (type) {
            SearchType.Movie -> moviesStateHolder
            SearchType.Series -> seriesStateHolder
            SearchType.Actors -> actorStateHolder
        }
    }

    fun updateKeyword(keyword: String) {
        currentStateHolder.updateKeyword(keyword)
    }

    fun onTryAgain() {
        moviesStateHolder
    }

}


enum class SearchType(val title: String) {
    Movie("Movies"),
    Series("Tv Shows"),
    Actors("Actors")
}