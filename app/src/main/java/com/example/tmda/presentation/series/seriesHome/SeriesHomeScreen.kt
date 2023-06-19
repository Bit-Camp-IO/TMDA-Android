package com.example.tmda.presentation.series.seriesHome

import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.tmda.presentation.navigation.Destinations
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

    val lifecycleOwner = LocalLifecycleOwner.current
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
   SideEffect {

       onBackPressedDispatcher?.addCallback(owner =lifecycleOwner) {

          navController. navigate(Destinations.MOVIES_ROUTE) {
               popUpTo(navController.graph.findStartDestination().id) {
                   saveState = true
               }
               launchSingleTop = true
               restoreState = true
           }
       }
   }

}
