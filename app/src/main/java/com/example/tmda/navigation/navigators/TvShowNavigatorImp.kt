package com.example.tmda.navigation.navigators

import androidx.navigation.NavController
import com.example.tmda.navigation.navigateToPersonScreen
import com.example.tmda.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.navigation.navigateToTvShowsListScreen
import com.example.tvshowfeature.navigation.TvShowNavigator
import com.example.tvshowfeature.seriesList.SeriesScreenType

class TvShowNavigatorImp(
    override val navController: NavController,
    override val baseRoute: String
) : TvShowNavigator {

    override fun navigateToTvShowDetailsScreen(tvShowId: Int) {
        navController.navigateToTvShowDetailsScreen(tvShowId)
    }

    override fun navigateToShowsListScreen(seriesScreenType: SeriesScreenType, seriesId: Int) {
        navController.navigateToTvShowsListScreen(seriesScreenType, seriesId)
    }

    override fun navigateToPersonScreen(personId: Int) {
        navController.navigateToPersonScreen(personId)
    }
}