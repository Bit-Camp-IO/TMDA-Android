package com.example.tmda.presentation.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.tmda.presentation.navigation.SeriesList.seriesListArg

const val moviesRoute = "moviesRoute"
const val moviesHomeScreen = "moviesHomeScreen"
const val movieDetailsScreen = "movieDetailsScreen"
const val movieListScreen = "movieListScreen"
const val seriesRoute = "seriesRoute"
const val seriesHomeScreen = "seriesHomeScreen"
const val seriesDetailsScreen = "seriesDetailsScreen"
const val seriesListScreen = "seriesListScreen"
const val searchRoute = "searchRoute"
const val searchScreen = "searchHomeScreen"
const val accountRoute = "userRoute"
const val accountScreen = "userInfoScreen"


object SeriesList {
    const val route = seriesListScreen
    const val seriesListArg = "see_all_type"
    const val routeWithArgs = "$route/{$seriesListArg}"
    val arguments = listOf(navArgument(seriesListArg) { type = NavType.IntType })
}

object SeriesDetails {
    const val route = seriesDetailsScreen
    const val seriesDetailsArg = "show_id"
    const val routeWithArgs = "$route/{$seriesDetailsArg}"
    val arguments = listOf(navArgument(seriesDetailsArg) { type = NavType.IntType })
}
fun NavHostController.navigateToSeriesListScreen(type: Int) {
    this.navigate("${SeriesList.route}/$type")
}
fun NavHostController.navigateToSeriesDetailsScreen(showId: Int) {
    this.navigate("${SeriesDetails.route}/$showId")
}
