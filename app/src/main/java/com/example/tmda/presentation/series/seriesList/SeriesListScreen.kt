package com.example.tmda.presentation.series.seriesList

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
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tmda.presentation.shared.AppToolBar
import com.example.tmda.presentation.shared.uiStates.ErrorScreen
import com.example.tmda.presentation.shared.uiStates.LoadingScreen


@Composable
fun SeriesListScreen(
    navController: NavController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val viewModel = hiltViewModel<SeriesListViewModel>()
    val pagingData = viewModel.getTvShowStream().collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

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
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        when (pagingData.loadState.refresh) {
            is LoadState.Error -> ErrorScreen { pagingData.retry() }
            LoadState.Loading -> LoadingScreen()
            is LoadState.NotLoading -> SeriesList(
                navController = navController,
                series = pagingData,
                addOrRemoveSeriesToSavedList = viewModel::addOrRemoveToBookMark
            )
        }
        AppToolBar(
            Modifier.padding(top = 16.dp),
            viewModel.seriesScreenType.screenTitle,
            navController
        ) {}
    }

}

