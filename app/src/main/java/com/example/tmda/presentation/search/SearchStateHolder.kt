@file:OptIn(FlowPreview::class)

package com.example.tmda.presentation.search

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmda.presentation.shared.paging.PagingProvider
import com.example.tmda.presentation.shared.paging.UiPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class SearchStateHolder<T : Any>(
    private val coroutineScope: CoroutineScope,
    private val responseGetter: suspend (String, Int) -> Result<UiPage<T>>
) {
    //
    private val _displayedKeyWord = mutableStateOf("")
    val displayedKeyWord: State<String>
        get() = _displayedKeyWord
    private val currentKeyWord = mutableStateOf("")

    //
    private val actionStream = MutableSharedFlow<String>()
    private val actionStreamDebounced = actionStream.debounce(700)

    //
    private val _searchPagingProvider = PagingProvider(::pageDateProvider)
    var searchPagingProvider: Flow<PagingData<T>> =
        _searchPagingProvider.createNewPager().flow.cachedIn(coroutineScope)
        private set


    val listState = LazyListState()

    //
    init {
        observeKeyword()
    }


    private suspend fun pageDateProvider(
        page: Int
    ): Result<UiPage<T>> {
        return responseGetter(currentKeyWord.value, page)
    }

    fun updateKeyword(keyword: String) {
        _displayedKeyWord.value = keyword
        coroutineScope.launch { listState.scrollToItem(0, 0) }

        coroutineScope.launch {
            actionStream.emit(keyword)
        }
    }

    private fun observeKeyword() {
        coroutineScope.launch(Dispatchers.Unconfined) {
            actionStreamDebounced.collect {
                currentKeyWord.value = it
                _searchPagingProvider.invalidate()
            }
        }

    }

    fun tryAgain() {
        _searchPagingProvider.invalidate()
    }

}