package com.example.moviesfeature.navigation

import androidx.navigation.NavController
import com.example.moviesfeature.uiModels.MoviesScreenType

interface MoviesNavigator {
    val navController: NavController
    fun navigateToMovieListScreen(
        screenTitle: String,
        moviesScreenType: MoviesScreenType,
        movieId: Int=-1
    )
    fun navigateToMovieDetails(
        movieId: Int
    )
    fun navigateToPersonScreen(
        personId: Int
    )
}