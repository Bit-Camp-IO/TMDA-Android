package com.example.tmda.navigation.navigators

import androidx.navigation.NavController
import com.example.moviesfeature.navigation.MoviesNavigator
import com.example.moviesfeature.uiModels.MoviesScreenType
import com.example.tmda.navigation.navigateToMovieDetails
import com.example.tmda.navigation.navigateToMovieListScreen
import com.example.tmda.navigation.navigateToPersonScreen

class MoviesNavigatorImpl(override val navController: NavController) : MoviesNavigator {


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

}