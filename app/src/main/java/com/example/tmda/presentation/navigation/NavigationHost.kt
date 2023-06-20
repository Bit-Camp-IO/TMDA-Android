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
import com.example.tmda.presentation.movies.person.PersonMovieScreen
import com.example.tmda.presentation.movies.uiModels.MoviesScreenType
import com.example.tmda.presentation.people.PersonSearchScreen
import com.example.tmda.presentation.profile.ProfileScreen
import com.example.tmda.presentation.search.SearchScreen
import com.example.tmda.presentation.series.person.PersonSeriesScreen
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
                "${Destinations.MOVIES_ROUTE+Destinations.MOVIES_DETAILS_SCREEN}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                    },
                )
            ) { MovieDetailsScreen(navController) }
            composable(
                "${Destinations.MOVIES_ROUTE+Destinations.MOVIES_LIST_SCREEN}/{$MOVIE_LIST_SCREEN_TITLE}/{$MOVIES_LIST_SCREEN_TYPE}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_LIST_SCREEN_TITLE) {
                        type = NavType.StringType
                    },
                    navArgument(MOVIES_LIST_SCREEN_TYPE) {
                        type = NavType.EnumType(MoviesScreenType::class.java)
                    },
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {
                MoviesListScreen(
                    it.arguments!!.getString(MOVIE_LIST_SCREEN_TITLE)!!,
                    navController,
                )
            }
            composable(
                route = "${Destinations.MOVIES_ROUTE+Destinations.PERSON_SCREEN}/{$PERSON_ID}",
                arguments = listOf(navArgument(PERSON_ID) {
                    type = NavType.IntType
                })

            ) { PersonMovieScreen(navController = navController) }
        }



        navigation(
            route = Destinations.SERIES_ROUTE,
            startDestination = Destinations.SERIES_HOME_SCREEN
        ) {
            composable(Destinations.SERIES_HOME_SCREEN) { SeriesHomeScreen(navController) }

            composable(
                "${Destinations.SERIES_ROUTE+Destinations.SERIES_LIST_SCREEN}/{$SERIES_LIST_SCREEN_TYPE}/{$SERIES_ID}",
                arguments = TvShowsList.arguments
            ) {
                SeriesListScreen(navController)
            }
            composable(
                "${Destinations.SERIES_ROUTE+Destinations.SERIES_DETAILS_SCREEN}/{$SERIES_ID}",
                arguments = listOf(navArgument(SERIES_ID) { type = NavType.IntType }
                )) {
                SeriesDetailsScreen(navController)
            }
            composable(
                route = "${Destinations.SERIES_ROUTE+Destinations.PERSON_SCREEN}/{$PERSON_ID}",
                arguments = listOf(navArgument(PERSON_ID) {
                    type = NavType.IntType
                })

            ) { PersonSeriesScreen(navController = navController) }

        }
        navigation(
            route = Destinations.SEARCH_ROUTE,
            startDestination = Destinations.SEARCH_SCREEN
        ) {
            composable(Destinations.SEARCH_SCREEN) { SearchScreen(navController = navController) }


            composable(
               "${Destinations.SEARCH_ROUTE+Destinations.SERIES_DETAILS_SCREEN}/{$SERIES_ID}",
                arguments = listOf(navArgument(SERIES_ID) { type = NavType.IntType }
            )) {
                SeriesDetailsScreen(navController)
            }
            composable(
                "${Destinations.SEARCH_ROUTE+Destinations.SERIES_LIST_SCREEN}/{$SERIES_LIST_SCREEN_TYPE}/{$SERIES_ID}",
                arguments = TvShowsList.arguments
            ) {
                SeriesListScreen(navController)
            }


            composable(
                "${Destinations.SEARCH_ROUTE+Destinations.MOVIES_LIST_SCREEN}/{$MOVIE_LIST_SCREEN_TITLE}/{$MOVIES_LIST_SCREEN_TYPE}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_LIST_SCREEN_TITLE) {
                        type = NavType.StringType
                    },
                    navArgument(MOVIES_LIST_SCREEN_TYPE) {
                        type = NavType.EnumType(MoviesScreenType::class.java)
                    },
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {
                MoviesListScreen(
                    it.arguments!!.getString(MOVIE_LIST_SCREEN_TITLE)!!,
                    navController,
                )
            }
            composable(
                "${Destinations.SEARCH_ROUTE+Destinations.MOVIES_DETAILS_SCREEN}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                    },
                )
            ) { MovieDetailsScreen(navController) }



            composable(
                route = "${Destinations.SEARCH_ROUTE+Destinations.PERSON_SCREEN}/{$PERSON_ID}",
                arguments = listOf(navArgument(PERSON_ID) { type = NavType.IntType })
            )
            { PersonSearchScreen(navController = navController) }
        }
        navigation(
            route = Destinations.ACCOUNT_ROUTE,
            startDestination = Destinations.ACCOUNT_SCREEN
        ) {
            composable(Destinations.ACCOUNT_SCREEN) { ProfileScreen(navController = navController)}
            composable(Destinations.MOVIES_DETAILS_SCREEN) { MovieDetailsScreen(navController) }
            composable(Destinations.MOVIES_LIST_SCREEN) {
                MoviesListScreen(
                    it.arguments!!.getString(MOVIE_LIST_SCREEN_TITLE)!!,
                    navController
                )
            }
            composable(Destinations.SERIES_DETAILS_SCREEN) {}
            composable(Destinations.SERIES_LIST_SCREEN) {}
        }

    }
}



