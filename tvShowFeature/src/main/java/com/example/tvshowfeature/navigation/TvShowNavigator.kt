package com.example.tvshowfeature.navigation

import androidx.navigation.NavController
import com.example.tvshowfeature.seriesList.SeriesScreenType

interface TvShowNavigator {
    val navController: NavController
    val baseRoute: String
    fun navigateToTvShowDetailsScreen(tvShowId: Int)
    fun navigateToShowsListScreen(
        seriesScreenType: SeriesScreenType,
        seriesId: Int = -1
    )

    fun navigateToPersonScreen(personId: Int)
}