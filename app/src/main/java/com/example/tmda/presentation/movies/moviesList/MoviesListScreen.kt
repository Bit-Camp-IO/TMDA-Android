package com.example.tmda.presentation.movies.moviesList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tmda.presentation.movies.MovieListTile
import com.example.tmda.presentation.movies.uiModels.MovieUiDto
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.shared.AppToolBar
import com.example.tmda.presentation.shared.base.list.BaseLazyColumn
import com.example.tmda.presentation.shared.uiStates.ErrorScreen
import com.example.tmda.presentation.shared.uiStates.LoadingScreen


@Composable
fun MoviesListScreen(
    title: String,
    navController: NavController,
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
                    navController = navController,
                    listState = lazyListState,
                    movies = movies,
                    addOrRemoveMovieToSavedList = viewModel::addOrRemoveMovieToSavedList
                )
            }
        }
        AppToolBar(Modifier.padding(top = 16.dp), title, navController) {}
    }


}


@Composable
fun MovieList(
    navController: NavController,
    listState: LazyListState = rememberLazyListState(),
    movies: LazyPagingItems<MovieUiDto>,
    addOrRemoveMovieToSavedList: suspend (Int, MutableState<Boolean>) -> Boolean

) {
    BaseLazyColumn(
        lazyItems = movies,
        listState = listState,
        keyGetter = { it.id },
        contentType = { MovieUiDto::class }
    ) {
        MovieListTile(
            movie = movies[it]!!,
            onCardClicked = navController::navigateToMovieDetails,
            onSaveItemClicked = addOrRemoveMovieToSavedList
        )
    }
}
