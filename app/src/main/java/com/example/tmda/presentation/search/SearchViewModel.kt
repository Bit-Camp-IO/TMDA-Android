package com.example.tmda.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.useCases.SearchSeriesUseCase
import com.example.movies.domain.useCases.SearchMoviesUseCase
import com.example.tmda.presentation.search.data.SearchItemModel
import com.example.tmda.presentation.search.data.toSearchItem
import com.example.tmda.presentation.shared.paging.UiPage
import com.example.tmda.presentation.shared.uiStates.mapToOtherType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchSeriesUseCase: SearchSeriesUseCase
) :
    ViewModel() {
    private val _searchType = mutableStateOf(SearchType.Movie)
    val searchType: State<SearchType>
        get() = _searchType

    //
    val moviesStateHolder = SearchStateHolder(viewModelScope, ::moviePageProvider)
    val seriesStateHolder = SearchStateHolder(viewModelScope, ::seriesPageProvider)
    var currentStateHolder = moviesStateHolder

    //
    private suspend fun seriesPageProvider(
        keyword: String,
        page: Int
    ): Result<UiPage<SearchItemModel>> {
        return searchSeriesUseCase.invoke(keyword, page).mapToOtherType { tvShowPage ->
            UiPage(
                page = tvShowPage.page,
                results = tvShowPage.results.map { it.toSearchItem() },
                totalPages = tvShowPage.totalPages,
              //  isError = false
            )
        }
    }

    private suspend fun moviePageProvider(
        keyword: String,
        page: Int
    ): Result<UiPage<SearchItemModel>> {
        return searchMoviesUseCase.invoke(keyword, page).mapToOtherType { it ->
            UiPage(
                page = it.page,
                results = it.results.map { it.toSearchItem() },
                totalPages = it.totalPages,
            //    isError = false
            )
        }
    }


    fun changeSearchType(type: SearchType) {
        if (_searchType.value == type) return
        _searchType.value = type
        currentStateHolder = when (type) {
            SearchType.Movie -> moviesStateHolder
            SearchType.Series -> seriesStateHolder
            SearchType.Actors -> TODO()
        }
    }

    fun updateKeyword(keyword: String) {
        currentStateHolder.updateKeyword(keyword)
    }
    fun onTryAgain(){
        moviesStateHolder
    }

}


enum class SearchType(val title: String) {
    Movie("Movies"),
    Series("Tv Shows"),
    Actors("Actors")
}