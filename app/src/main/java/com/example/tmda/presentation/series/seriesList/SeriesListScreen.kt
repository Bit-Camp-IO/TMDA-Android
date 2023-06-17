package com.example.tmda.presentation.series.seriesList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tmda.presentation.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.presentation.series.SeriesCard
import com.example.tmda.presentation.series.uiDto.TvShowBookMarkUiModel


@Composable
fun SeriesListScreen(
    navController: NavController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val viewModel = hiltViewModel<SeriesListViewModel>()
    val pagingData = viewModel.getTvShowStream().collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    SeriesListScreen(pagingData, Modifier,navController,viewModel::addOrRemoveToBookMark)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val priorityStart = lazyListState.firstVisibleItemIndex
                val priorityEnd =
                    priorityStart + lazyListState.layoutInfo.visibleItemsInfo.lastIndex
                val priorityRange = priorityStart until priorityEnd
                viewModel.updateIsSavedState(pagingData.itemSnapshotList.items, priorityRange)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}

@Composable
fun SeriesListScreen(
    seriesData: LazyPagingItems<TvShowBookMarkUiModel>,
    modifier: Modifier = Modifier,
    navController: NavController,
    onBookmarkClick:suspend (Int,MutableState<Boolean>) -> Boolean,
) {



    LazyColumn(modifier = modifier.fillMaxSize()) {
        val loadState = seriesData.loadState.source
        when (loadState.refresh) {
            LoadState.Loading -> {
                item {
                    Box(Modifier.fillParentMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }

            is LoadState.Error -> {
                val errMsg =
                    if ((loadState.refresh as LoadState.Error).error is java.io.IOException)
                        "No connection" else "An unknown error has occurred\nplease try again later"
                item {
                    Column(
                        Modifier.fillParentMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error icon"
                        )
                        Text(
                            text = errMsg,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }

            is LoadState.NotLoading -> {}
        }

        items(seriesData.itemCount) {
            seriesData[it]?.let { tvShow ->
                SeriesCard(tvShow,navController::navigateToTvShowDetailsScreen, onBookmarkClick)
            }

        }

    }

}

