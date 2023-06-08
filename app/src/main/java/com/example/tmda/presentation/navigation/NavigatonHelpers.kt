package com.example.tmda.presentation.navigation

import androidx.navigation.NavController
import com.example.tmda.presentation.movies.moviesList.ScreenType


fun NavController.navigateToMovieListScreen(
    screenTitle: String,
    screenType: ScreenType
) {
    navigate("${Destinations.MOVIES_LIST_SCREEN}/$screenTitle/$screenType/${-1}")
}

fun NavController.navigateToMovieDetails(movieId: Int) {
    navigate("${Destinations.MOVIES_DETAILS_SCREEN}/$movieId")
}
