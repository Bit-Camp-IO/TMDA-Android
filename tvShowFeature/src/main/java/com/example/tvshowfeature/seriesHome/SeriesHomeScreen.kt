package com.example.tvshowfeature.seriesHome

import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.tvshowfeature.navigation.TvShowNavigator
import com.example.tvshowfeature.seriesList.SeriesScreenType

@Composable
fun SeriesHomeScreen(
    tvShowNavigator: TvShowNavigator
) {
    val viewModel = hiltViewModel<SeriesHomeViewModel>()
    if (viewModel.isErrorState()) com.example.sharedui.uiStates.ErrorScreen(viewModel::updateAll)
    else LazyColumn {
        item { NowPlayingHeader(viewModel.trendingUiState.value,tvShowNavigator::navigateToTvShowDetailsScreen) }

        item {
            SeriesHomeLazyRow(
                title = "Popular",
                itemsState = viewModel.popularUiState.value,
                onSeeAllClicked = { tvShowNavigator.navigateToShowsListScreen(SeriesScreenType.Popular) },
                onCardItemClicked = tvShowNavigator::navigateToTvShowDetailsScreen
            )
        }
        item {
            SeriesHomeLazyRow(
                title = "On The Air",
                itemsState = viewModel.onTheAirUiState.value,
                onSeeAllClicked = { tvShowNavigator.navigateToShowsListScreen(SeriesScreenType.OnTheAir) },
                onCardItemClicked = tvShowNavigator::navigateToTvShowDetailsScreen
            )
        }
        item {
            SeriesHomeLazyRow(
                title = "Top Rated",
                itemsState = viewModel.topRatedUiState.value,
                hasBottomDivider = false,
                onSeeAllClicked = { tvShowNavigator.navigateToShowsListScreen(SeriesScreenType.TopRated) },
                onCardItemClicked = tvShowNavigator::navigateToTvShowDetailsScreen
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
   SideEffect {

       onBackPressedDispatcher?.addCallback(owner =lifecycleOwner) {

          tvShowNavigator.navController.navigate(tvShowNavigator.baseRoute) {
               popUpTo(tvShowNavigator.navController.graph.findStartDestination().id) {
                   saveState = true
               }
               launchSingleTop = true
               restoreState = true
           }
       }
   }

}
