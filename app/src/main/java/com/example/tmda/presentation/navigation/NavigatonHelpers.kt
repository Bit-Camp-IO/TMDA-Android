package com.example.tmda.presentation.navigation

import androidx.navigation.NavController
import com.example.tmda.presentation.movies.moviesList.ScreenType


fun NavController.navigateToMovieListScreen(
    screenTitle: String,
    screenType: ScreenType,
    id:Int=-1
) {
    navigate("${Destinations.MOVIES_LIST_SCREEN}/$screenTitle/$screenType/${id}")
}

fun NavController.navigateToMovieDetails(movieId: Int) {
    navigate("${Destinations.MOVIES_DETAILS_SCREEN}/$movieId")
}
