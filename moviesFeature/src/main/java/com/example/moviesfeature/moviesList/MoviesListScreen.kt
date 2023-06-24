package com.example.moviesfeature.moviesList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesfeature.navigation.MoviesNavigator
import com.example.moviesfeature.uiModels.MovieUiDto
import com.example.sharedui.x.AppToolBar
import com.example.sharedui.uiStates.ErrorScreen
import com.example.sharedui.uiStates.LoadingScreen

@Composable
fun MoviesListScreen(
    title: String,
    moviesNavigator: MoviesNavigator,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val viewModel = hiltViewModel<MoviesListViewModel>()
    val movies: LazyPagingItems<MovieUiDto> =
        viewModel.getPagesStream().collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val priorityStart = lazyListState.firstVisibleItemIndex
                val priorityEnd =
                    priorityStart + lazyListState.layoutInfo.visibleItemsInfo.lastIndex
                val priorityRange = priorityStart until priorityEnd
                viewModel.updateIsSavedState(movies.itemSnapshotList.items, priorityRange)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }

    }
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        when (movies.loadState.refresh) {
            is LoadState.Error -> ErrorScreen { movies.retry() }
            is LoadState.Loading -> LoadingScreen()
            else -> {
                MovieList(
                    moviesNavigator = moviesNavigator,
                    listState = lazyListState,
                    movies = movies,
                    addOrRemoveMovieToSavedList = viewModel::addOrRemoveMovieToSavedList
                )
            }
        }
        AppToolBar(Modifier.padding(top = 16.dp), title, moviesNavigator.navController) {}
    }


}


