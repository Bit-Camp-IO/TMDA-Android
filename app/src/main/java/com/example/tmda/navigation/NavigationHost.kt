package com.example.tmda.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.moviesfeature.movieDetails.MovieDetailsScreen
import com.example.moviesfeature.moviesList.MoviesListScreen
import com.example.moviesfeature.navigation.MoviesNavigator
import com.example.moviesfeature.uiModels.MoviesScreenType
import com.example.profilefeature.navigation.ProfileNavigator
import com.example.profilefeature.person.PersonProfileScreen
import com.example.profilefeature.profile.ProfileScreen
import com.example.searchfeature.SearchScreen
import com.example.searchfeature.navigation.SearchNavigator
import com.example.searchfeature.person.PersonSearchScreen
import com.example.tmda.navigation.navigators.MoviesNavigatorImpl
import com.example.tmda.navigation.navigators.ProfileNavigatorImpl
import com.example.tmda.navigation.navigators.SearchNavigatorImpl
import com.example.tmda.navigation.navigators.TvShowNavigatorImp
import com.example.tvshowfeature.navigation.TvShowNavigator
import com.example.tvshowfeature.person.PersonSeriesScreen
import com.example.tvshowfeature.seriesDetails.SeriesDetailsScreen
import com.example.tvshowfeature.seriesList.SeriesListScreen


@Composable
fun NavigationHost(navController: NavHostController) {
    val moviesNavigator: MoviesNavigator = remember { MoviesNavigatorImpl(navController) }
    val tvShowNavigator: TvShowNavigator =
        remember { TvShowNavigatorImp(navController, Destinations.MOVIES_ROUTE) }
    val searchNavigator: SearchNavigator =
        remember { SearchNavigatorImpl(navController, Destinations.MOVIES_ROUTE) }
    val profileNavigator: ProfileNavigator =
        remember { ProfileNavigatorImpl(navController, Destinations.MOVIES_ROUTE) }


    NavHost(navController = navController, startDestination = Destinations.MOVIES_ROUTE) {
        navigation(
            route = Destinations.MOVIES_ROUTE,
            startDestination = Destinations.MOVIES_HOME_SCREEN
        ) {
            composable(Destinations.MOVIES_HOME_SCREEN) {
                com.example.moviesfeature.moviesHome.MoviesHomeScreen(
                    moviesNavigator
                )
            }
            composable(
                "${Destinations.MOVIES_ROUTE + Destinations.MOVIES_DETAILS_SCREEN}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                    },
                )
            ) { MovieDetailsScreen(moviesNavigator) }
            composable(
                "${Destinations.MOVIES_ROUTE + Destinations.MOVIES_LIST_SCREEN}/{$MOVIE_LIST_SCREEN_TITLE}/{$MOVIES_LIST_SCREEN_TYPE}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_LIST_SCREEN_TITLE) {
                        type = NavType.StringType
                    },
                    navArgument(MOVIES_LIST_SCREEN_TYPE) {
                        type =
                            NavType.EnumType(MoviesScreenType::class.java)
                    },
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {
                MoviesListScreen(
                    it.arguments!!.getString(MOVIE_LIST_SCREEN_TITLE)!!,
                    moviesNavigator,
                )
            }
            composable(
                route = "${Destinations.MOVIES_ROUTE + Destinations.PERSON_SCREEN}/{$PERSON_ID}",
                arguments = listOf(navArgument(PERSON_ID) {
                    type = NavType.IntType
                })

            ) { com.example.moviesfeature.person.PersonMovieScreen(moviesNavigator) }
        }



        navigation(
            route = Destinations.SERIES_ROUTE,
            startDestination = Destinations.SERIES_HOME_SCREEN
        ) {
            composable(Destinations.SERIES_HOME_SCREEN) {
                com.example.tvshowfeature.seriesHome.SeriesHomeScreen(
                    tvShowNavigator
                )
            }

            composable(
                "${Destinations.SERIES_ROUTE + Destinations.SERIES_LIST_SCREEN}/{$SERIES_LIST_SCREEN_TYPE}/{$SERIES_ID}",
                arguments = TvShowsList.arguments
            ) {
                SeriesListScreen(tvShowNavigator)
            }
            composable(
                "${Destinations.SERIES_ROUTE + Destinations.SERIES_DETAILS_SCREEN}/{$SERIES_ID}",
                arguments = listOf(navArgument(SERIES_ID) { type = NavType.IntType }
                )) { SeriesDetailsScreen(tvShowNavigator) }
            composable(
                route = "${Destinations.SERIES_ROUTE + Destinations.PERSON_SCREEN}/{$PERSON_ID}",
                arguments = listOf(navArgument(PERSON_ID) {
                    type = NavType.IntType
                })
            ) { PersonSeriesScreen(tvShowNavigator) }

        }

        navigation(
            route = Destinations.SEARCH_ROUTE,
            startDestination = Destinations.SEARCH_SCREEN
        ) {
            composable(Destinations.SEARCH_SCREEN) {
                SearchScreen(searchNavigator)
            }


            composable(
                "${Destinations.SEARCH_ROUTE + Destinations.SERIES_DETAILS_SCREEN}/{$SERIES_ID}",
                arguments = listOf(navArgument(SERIES_ID) { type = NavType.IntType }
                )) { SeriesDetailsScreen(searchNavigator) }
            composable(
                "${Destinations.SEARCH_ROUTE + Destinations.SERIES_LIST_SCREEN}/{$SERIES_LIST_SCREEN_TYPE}/{$SERIES_ID}",
                arguments = TvShowsList.arguments
            ) { SeriesListScreen(searchNavigator) }


            composable(
                "${Destinations.SEARCH_ROUTE + Destinations.MOVIES_LIST_SCREEN}/{$MOVIE_LIST_SCREEN_TITLE}/{$MOVIES_LIST_SCREEN_TYPE}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_LIST_SCREEN_TITLE) {
                        type = NavType.StringType
                    },
                    navArgument(MOVIES_LIST_SCREEN_TYPE) {
                        type =
                            NavType.EnumType(MoviesScreenType::class.java)
                    },
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {
                MoviesListScreen(
                    it.arguments!!.getString(MOVIE_LIST_SCREEN_TITLE)!!,
                    searchNavigator
                )
            }
            composable(
                "${Destinations.SEARCH_ROUTE + Destinations.MOVIES_DETAILS_SCREEN}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                    },
                )
            ) { MovieDetailsScreen(profileNavigator) }



            composable(
                route = "${Destinations.SEARCH_ROUTE + Destinations.PERSON_SCREEN}/{$PERSON_ID}",
                arguments = listOf(navArgument(PERSON_ID) { type = NavType.IntType })
            )
            { PersonSearchScreen(searchNavigator) }
        }
        navigation(
            route = Destinations.ACCOUNT_ROUTE,
            startDestination = Destinations.ACCOUNT_SCREEN
        ) {
            composable(Destinations.ACCOUNT_SCREEN) {
                ProfileScreen(
                    profileNavigator
                )
            }

            composable(
                "${Destinations.ACCOUNT_ROUTE + Destinations.MOVIES_DETAILS_SCREEN}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                    },
                )
            ) { MovieDetailsScreen(profileNavigator) }
            composable(
                "${Destinations.ACCOUNT_ROUTE + Destinations.MOVIES_LIST_SCREEN}/{$MOVIE_LIST_SCREEN_TITLE}/{$MOVIES_LIST_SCREEN_TYPE}/{$MOVIE_ID}",
                arguments = listOf(
                    navArgument(MOVIE_LIST_SCREEN_TITLE) {
                        type = NavType.StringType
                    },
                    navArgument(MOVIES_LIST_SCREEN_TYPE) {
                        type =
                            NavType.EnumType(MoviesScreenType::class.java)
                    },
                    navArgument(MOVIE_ID) {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {
              MoviesListScreen(
                    it.arguments!!.getString(MOVIE_LIST_SCREEN_TITLE)!!,
                    profileNavigator,
                )
            }
            composable(
                "${Destinations.ACCOUNT_ROUTE + Destinations.SERIES_LIST_SCREEN}/{$SERIES_LIST_SCREEN_TYPE}/{$SERIES_ID}",
                arguments = TvShowsList.arguments
            ) {
                SeriesListScreen(profileNavigator)
            }
            composable(
                "${Destinations.ACCOUNT_ROUTE + Destinations.SERIES_DETAILS_SCREEN}/{$SERIES_ID}",
                arguments = listOf(navArgument(SERIES_ID) { type = NavType.IntType }
                )) {
                SeriesDetailsScreen(profileNavigator)
            }
            composable(
                route = "${Destinations.ACCOUNT_ROUTE + Destinations.PERSON_SCREEN}/{$PERSON_ID}",
                arguments = listOf(navArgument(PERSON_ID) { type = NavType.IntType })
            )
            { PersonProfileScreen(profileNavigator) }

        }

    }

}