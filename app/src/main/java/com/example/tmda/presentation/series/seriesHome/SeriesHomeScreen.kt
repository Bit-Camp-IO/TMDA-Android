package com.example.tmda.presentation.series.seriesHome

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmda.presentation.navigation.navigateToShowsListScreen
import com.example.tmda.presentation.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.presentation.series.seriesList.SeriesScreenType
import com.example.tmda.presentation.shared.uiStates.ErrorScreen

@Composable
fun SeriesHomeScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<SeriesHomeViewModel>()
    if (viewModel.isErrorState()) ErrorScreen(viewModel::updateAll)
    else LazyColumn {
        item { NowPlayingHeader(viewModel.trendingUiState.value) }

        item {
            SeriesHomeLazyRow(
                title = "Popular",
                itemsState = viewModel.popularUiState.value,
                onSeeAllClicked = { navController.navigateToShowsListScreen(SeriesScreenType.Popular) },
                onCardItemClicked = navController::navigateToTvShowDetailsScreen
            )
        }
        item {
            SeriesHomeLazyRow(
                title = "On The Air",
                itemsState = viewModel.onTheAirUiState.value,
                onSeeAllClicked = { navController.navigateToShowsListScreen(SeriesScreenType.OnTheAir) },
                onCardItemClicked = navController::navigateToTvShowDetailsScreen
            )
        }
        item {
            SeriesHomeLazyRow(
                title = "Top Rated",
                itemsState = viewModel.topRatedUiState.value,
                hasBottomDivider = false,
                onSeeAllClicked = { navController.navigateToShowsListScreen(SeriesScreenType.TopRated) },
                onCardItemClicked = navController::navigateToTvShowDetailsScreen
            )
        }
    }

}
