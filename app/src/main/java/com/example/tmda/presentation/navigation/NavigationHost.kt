package com.example.tmda.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.tmda.presentation.movies.movieDetails.MovieDetailsScreen
import com.example.tmda.presentation.movies.moviesHome.MoviesHomeScreen
import com.example.tmda.presentation.movies.moviesList.MoviesListScreen
import com.example.tmda.presentation.series.seriesDetails.SeriesDetailsScreen
import com.example.tmda.presentation.series.seriesHome.SeriesHomeScreen
import com.example.tmda.presentation.series.seriesList.SeriesListScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = seriesRoute) {
        navigation(route = moviesRoute, startDestination = moviesHomeScreen) {
            composable(moviesHomeScreen) { MoviesHomeScreen() }
            composable(movieDetailsScreen) { MovieDetailsScreen() }
            composable(movieListScreen) { MoviesListScreen() }
        }
        navigation(route = seriesRoute, startDestination = seriesHomeScreen) {
            composable(seriesHomeScreen) {
                SeriesHomeScreen(
                    onSeeAllClick = {
                        navController.navigateToSeriesListScreen(it)
                    }, onTvShowClick = {
                        navController.navigateToSeriesDetailsScreen(it)
                    }
                )
            }
            composable(SeriesList.routeWithArgs, arguments = SeriesList.arguments) {
                val categoryType = it.arguments?.getInt(SeriesList.seriesListArg)!!
                SeriesListScreen(categoryType)

            }
            composable(SeriesDetails.routeWithArgs, arguments = SeriesDetails.arguments){
                val showId = it.arguments?.getInt(SeriesDetails.seriesDetailsArg)!!
                SeriesDetailsScreen(showId)
            }
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



