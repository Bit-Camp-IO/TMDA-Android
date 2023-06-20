package com.example.tmda.presentation.navigation

import androidx.navigation.NavController
import com.example.tmda.presentation.movies.uiModels.MoviesScreenType
import com.example.tmda.presentation.series.seriesList.SeriesScreenType


fun NavController.navigateToMovieListScreen(

    screenTitle: String,
    moviesScreenType: MoviesScreenType,
    id: Int = -1
) {
    val parent = currentBackStackEntry?.destination?.parent?.route
    navigate("${parent+Destinations.MOVIES_LIST_SCREEN}/$screenTitle/$moviesScreenType/${id}")
}

fun NavController.navigateToMovieDetails(movieId: Int) {
    val parent = currentBackStackEntry?.destination?.parent?.route
    navigate("${parent+Destinations.MOVIES_DETAILS_SCREEN}/$movieId")
}

fun NavController.navigateToPersonScreen(personId: Int) {
    val parent = currentBackStackEntry?.destination?.parent?.route
    navigate("${parent+Destinations.PERSON_SCREEN}/$personId")
}

//
fun NavController.navigateToShowsListScreen(seriesScreenType: SeriesScreenType, id: Int = -1) {
    val parent = currentBackStackEntry?.destination?.parent?.route
    navigate("${parent+Destinations.SERIES_LIST_SCREEN}/$seriesScreenType/$id")
}

fun NavController.navigateToTvShowDetailsScreen(seriesId: Int) {
    val parent = currentBackStackEntry?.destination?.parent?.route

    navigate("${parent+Destinations.SERIES_DETAILS_SCREEN}/$seriesId")
}

//fun NavController.navigateToPersonScreen(personId: Int) {
//    navigate("${Destinations.PERSON_SCREEN}/$personId")
//}

//fun NavController.navigateToPersonScreen(personId: Int) {
//    navigate("${Destinations.PERSON_SCREEN}/$personId")
//}