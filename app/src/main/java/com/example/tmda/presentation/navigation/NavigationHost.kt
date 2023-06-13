package com.example.tmda.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.tmda.presentation.movies.movieDetails.MovieDetailsScreen
import com.example.tmda.presentation.movies.moviesHome.MoviesHomeScreen
import com.example.tmda.presentation.movies.moviesList.MoviesListScreen
import com.example.tmda.presentation.movies.moviesList.ScreenType
import com.example.tmda.presentation.series.seriesDetails.SeriesDetailsScreen
import com.example.tmda.presentation.series.seriesHome.SeriesHomeScreen
import com.example.tmda.presentation.series.seriesList.SeriesListScreen


@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.MOVIES_ROUTE) {
        navigation(
            route = Destinations.MOVIES_ROUTE,
            startDestination = Destinations.MOVIES_HOME_SCREEN
        ) {
            composable(Destinations.MOVIES_HOME_SCREEN) { MoviesHomeScreen(navController) }
            composable(
                "${Destinations.MOVIES_DETAILS_SCREEN}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                    },
                )
            ) { MovieDetailsScreen(navController) }
            composable(
                "${Destinations.MOVIES_LIST_SCREEN}/{$MOVIE_LIST_SCREEN_TITLE}/{$MOVIES_LIST_SCREEN_ID}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_LIST_SCREEN_TITLE) {
                        type = NavType.StringType
                    },
                    navArgument(MOVIES_LIST_SCREEN_ID) {
                        type = NavType.EnumType(ScreenType::class.java)
                    },
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {

                MoviesListScreen(
                    it.arguments!!.getString(MOVIE_LIST_SCREEN_TITLE)!!,
                    navController,
                    it.savedStateHandle,


                    )
            }
        }
        navigation(
            route = Destinations.SERIES_ROUTE,
            startDestination = Destinations.SERIES_HOME_SCREEN
        ) {
            composable(Destinations.SERIES_HOME_SCREEN) {
                SeriesHomeScreen(onSeeAllClick = {
                    navController.navigateToShowsListScreen(it)
                }, onTvShowClick = {
                    navController.navigateToTvShowDetailsScreen(it)
                })
            }
            composable(
                TvShowsList.routeWithArgs,
                arguments = TvShowsList.arguments
            ) {
                val id = it.arguments?.getInt(TvShowsList.tvShowTypeArg)!!
                SeriesListScreen(id)
            }
            composable(
                TvShowDetails.routeWithArgs,
                arguments = TvShowDetails.arguments
            ) {
                val id = it.arguments?.getInt(TvShowDetails.tvShowIdArg)!!
                SeriesDetailsScreen(id)
            }

        }
        navigation(
            route = Destinations.SEARCH_ROUTE,
            startDestination = Destinations.SEARCH_SCREEN
        ) {
            composable(Destinations.SEARCH_SCREEN) {}
            composable(Destinations.MOVIES_DETAILS_SCREEN) { MovieDetailsScreen(navController) }
            composable(Destinations.MOVIES_LIST_SCREEN) {
                MoviesListScreen(
                    it.arguments!!.getString(MOVIE_LIST_SCREEN_TITLE)!!,
                    navController,
                    it.savedStateHandle
                )
            }
            composable(Destinations.SERIES_DETAILS_SCREEN) {}
            composable(Destinations.SERIES_LIST_SCREEN) {}
        }
        navigation(
            route = Destinations.ACCOUNT_ROUTE,
            startDestination = Destinations.ACCOUNT_SCREEN
        ) {
            composable(Destinations.ACCOUNT_SCREEN) {}
            composable(Destinations.MOVIES_DETAILS_SCREEN) { MovieDetailsScreen(navController) }
            composable(Destinations.MOVIES_LIST_SCREEN) {
                MoviesListScreen(
                    it.arguments!!.getString(MOVIE_LIST_SCREEN_TITLE)!!,
                    navController,
                    it.savedStateHandle
                )
            }
            composable(Destinations.SERIES_DETAILS_SCREEN) {}
            composable(Destinations.SERIES_LIST_SCREEN) {}
        }

    }
}



