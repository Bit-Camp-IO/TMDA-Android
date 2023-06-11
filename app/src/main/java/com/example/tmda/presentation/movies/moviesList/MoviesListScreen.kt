package com.example.tmda.presentation.movies.moviesList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tmda.presentation.movies.MovieCard
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.shared.AppToolBar
import com.example.tmda.presentation.shared.LoadingScreen
import kotlinx.coroutines.delay


@Composable
fun MoviesListScreen(
    title: String,
    navController: NavController,
    savedStateHandle: SavedStateHandle
) {
    Log.d("xxxxxx","xxxxxxx")
    val viewModel = hiltViewModel<MoviesListViewModel>()
    val movies: LazyPagingItems<MovieUiDto> =
        viewModel.getPagesStream().collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        if (viewModel.isFirstCompose) {
            viewModel.isFirstCompose = false
            delay(100)
            return@LaunchedEffect
        }
        val priorityStart = lazyListState.firstVisibleItemIndex
        val priorityEnd = priorityStart + lazyListState.layoutInfo.visibleItemsInfo.lastIndex
        val priorityRange = priorityStart until  priorityEnd
        viewModel.updateIsSavedState(movies.itemSnapshotList.items, priorityRange)
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        when (movies.loadState.refresh) {
            is LoadState.Error -> {}
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
        AppToolBar(Modifier.padding(top = 16.dp), title,navController) {}
    }


}


@Composable
fun MovieList(

    navController: NavController,
    listState: LazyListState,
    movies: LazyPagingItems<MovieUiDto>,
    addOrRemoveMovieToSavedList: (Int, Boolean) -> Unit

) {
    LazyColumn(
        Modifier
            .background(Color.Transparent)
            .fillMaxSize(),
        state = listState
    ) {

        item { Spacer(modifier = Modifier.height(64.dp)) }
        items(
            count = movies.itemSnapshotList.size,
            key = {it},

        ) {

            MovieCard(
                movie = movies[it]!!,
                onCardClicked = navController::navigateToMovieDetails,
                onSaveItemClicked = addOrRemoveMovieToSavedList
            )
        }

        when (val state = movies.loadState.append) {
            is LoadState.Error -> {

            }

            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {

                        LoadingScreen()
                    }
                }
            }

            else -> {}
        }
    }
}







