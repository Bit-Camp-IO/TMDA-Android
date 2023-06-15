package com.example.tmda.presentation.series.seriesList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.tmda.presentation.series.SeriesCard
import com.example.tmda.presentation.series.uiDto.TvShowUiModel


@Composable
fun SeriesListScreen(
   navController: NavController
) {
    val viewModel = hiltViewModel<SeriesListViewModel>()
    val pagingData = viewModel.getTvShowStream().collectAsLazyPagingItems()
         SeriesListScreen(pagingData, Modifier) {}
}

@Composable
fun SeriesListScreen(
    seriesData: LazyPagingItems<TvShowUiModel>,
    modifier: Modifier = Modifier,
    onBookmarkClick: (TvShow) -> Unit,
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
                val errMsg = if ((loadState.refresh as LoadState.Error).error is java.io.IOException)
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

        items(seriesData.itemCount) { it ->
            seriesData[it]?.let { tvShow ->
                SeriesCard(tvShow) { onBookmarkClick(it) }
            }

        }

    }

}

