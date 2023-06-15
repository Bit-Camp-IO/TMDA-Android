package com.example.tmda.presentation.navigation

import androidx.navigation.NavController
import com.example.tmda.presentation.movies.moviesList.MoviesScreenType
import com.example.tmda.presentation.series.seriesList.SeriesScreenType


fun NavController.navigateToMovieListScreen(
    screenTitle: String,
    moviesScreenType: MoviesScreenType,
    id: Int = -1
) {
    navigate("${Destinations.MOVIES_LIST_SCREEN}/$screenTitle/$moviesScreenType/${id}")
}

fun NavController.navigateToMovieDetails(movieId: Int) {
    navigate("${Destinations.MOVIES_DETAILS_SCREEN}/$movieId")
}


fun NavController.navigateToShowsListScreen(seriesScreenType: SeriesScreenType, id: Int=-1) {
    navigate("${Destinations.SERIES_LIST_SCREEN}/$seriesScreenType/$id")
}

fun NavController.navigateToTvShowDetailsScreen(movieId: Int) {
    navigate("${TvShowDetails.route}/$movieId")
}
