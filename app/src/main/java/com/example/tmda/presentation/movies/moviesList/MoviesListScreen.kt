package com.example.tmda.presentation.movies.moviesList

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.tmda.presentation.movies.MovieCard
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.shared.AppToolBar
import com.example.tmda.presentation.shared.UiStates.ErrorScreen
import com.example.tmda.presentation.shared.UiStates.LoadingScreen
import com.example.tmda.ui.theme.PineGreenMedium


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
                    hasBookMark = true,
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
    hasBookMark: Boolean,
    addOrRemoveMovieToSavedList:suspend (Int, Boolean) -> Boolean = { _, _ -> false}

) {
    LazyColumn(
        Modifier
            .background(Color.Transparent)
            .fillMaxSize(),
        state = listState
    ) {

        item { Spacer(modifier = Modifier.height(80.dp)) }
        items(
            count = movies.itemSnapshotList.size,
            contentType = { MovieUiDto::class },
            key = { movies.peek(it)!!.id },

            ) {

            MovieCard(
                movie = movies[it]!!,
                hasBookMark,
                onCardClicked = navController::navigateToMovieDetails,
                onSaveItemClicked = addOrRemoveMovieToSavedList
            )
        }

        when (movies.loadState.append) {
            is LoadState.Error -> {
                item { ErrorComponent {movies.retry()} }
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

          //  else -> {}
            is LoadState.NotLoading -> {}
        }
    }
}
@Composable
fun ErrorComponent(onTryAgain:()->Unit){
    Button(
        onClick = onTryAgain,
        modifier = Modifier.height(160.dp).fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PineGreenMedium)
    ) {
        Text(text = "Try Again")
    }
}