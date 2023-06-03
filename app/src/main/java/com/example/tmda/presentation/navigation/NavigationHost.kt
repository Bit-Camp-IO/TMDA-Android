package com.example.tmda.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.tmda.presentation.movies.movieDetails.MovieDetailsScreen
import com.example.tmda.presentation.movies.moviesHome.MoviesHomeScreen
import com.example.tmda.presentation.movies.moviesList.MoviesListScreen

@Composable
fun NavigationHost( navController: NavHostController) {
    NavHost(navController = navController, startDestination = moviesRoute) {
        navigation(route = moviesRoute, startDestination = moviesHomeScreen) {
            composable(moviesHomeScreen) {
                MoviesHomeScreen()
            }
            composable(movieDetailsScreen) { MovieDetailsScreen() }
            composable(movieListScreen) { MoviesListScreen() }
        }
        navigation(route = seriesRoute, startDestination = seriesHomeScreen) {
            composable(seriesHomeScreen) { MoviesListScreen() }
            composable(seriesDetailsScreen) {}
            composable(seriesListScreen) {}
        }
        navigation(route = searchRoute, startDestination = searchScreen) {
            composable(searchScreen) {}
            composable(movieDetailsScreen) { MovieDetailsScreen() }
            composable(movieListScreen) { MoviesListScreen() }
            composable(seriesDetailsScreen) {}
            composable(seriesListScreen) {}
        }
        navigation(route = accountRoute, startDestination = accountScreen) {
            composable(accountScreen) {}
            composable(movieDetailsScreen) { MovieDetailsScreen() }
            composable(movieListScreen) { MoviesListScreen() }
            composable(seriesDetailsScreen) {}
            composable(seriesListScreen) {}
        }

    }
}



