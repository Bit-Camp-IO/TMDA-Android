package com.example.tmda.navigation.navigators

import androidx.navigation.NavController
import com.example.moviesfeature.uiModels.MoviesScreenType
import com.example.searchfeature.navigation.SearchNavigator
import com.example.tmda.navigation.navigateToMovieDetails
import com.example.tmda.navigation.navigateToMovieListScreen
import com.example.tmda.navigation.navigateToPersonScreen
import com.example.tmda.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.navigation.navigateToTvShowsListScreen
import com.example.tvshowfeature.seriesList.SeriesScreenType

class SearchNavigatorImpl(
    override val navController: NavController,
    override val baseRoute: String
) : SearchNavigator {


    override fun navigateToMovieListScreen(
        screenTitle: String,
        moviesScreenType: MoviesScreenType,
        movieId: Int
    ) {
        navController.navigateToMovieListScreen(screenTitle, moviesScreenType, movieId)
    }

    override fun navigateToMovieDetails(movieId: Int) {
        navController.navigateToMovieDetails(movieId)
    }

    override fun navigateToPersonScreen(personId: Int) {
        navController.navigateToPersonScreen(personId)
    }


    override fun navigateToTvShowDetailsScreen(tvShowId: Int) {
        navController.navigateToTvShowDetailsScreen(tvShowId)
    }

    override fun navigateToShowsListScreen(seriesScreenType: SeriesScreenType, seriesId: Int) {
        navController.navigateToTvShowsListScreen(seriesScreenType, seriesId)
    }
}