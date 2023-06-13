package com.example.tmda.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument


object Destinations {
    const val MOVIES_ROUTE = "moviesRoute"
    const val MOVIES_HOME_SCREEN = "moviesHomeScreen"
    const val MOVIES_DETAILS_SCREEN = "movieDetailsScreen"
    const val MOVIES_LIST_SCREEN = "movieListScreen"
    const val SERIES_ROUTE = "seriesRoute"
    const val SERIES_HOME_SCREEN = "seriesHomeScreen"
    const val SERIES_DETAILS_SCREEN = "seriesDetailsScreen"
    const val SERIES_LIST_SCREEN = "seriesListScreen"
    const val SEARCH_ROUTE = "searchRoute"
    const val SEARCH_SCREEN = "searchHomeScreen"
    const val ACCOUNT_ROUTE = "userRoute"
    const val ACCOUNT_SCREEN = "userInfoScreen"
}

//movie list screen arguments names
const val MOVIES_LIST_SCREEN_ID = "movieListScreenType"
const val MOVIE_ID = "movieId"
const val MOVIE_LIST_SCREEN_TITLE = "movieListScreenTitle"


object TvShowsList {
    const val route = "tvshows"
    const val tvShowTypeArg = "tvshow_type"
    const val routeWithArgs = "$route/{$tvShowTypeArg}"
    val arguments = listOf(navArgument(tvShowTypeArg) { type = NavType.IntType })
}
object TvShowDetails {
    const val route = "tvshow_details"
    const val tvShowIdArg = "tvshow_id"
    const val routeWithArgs = "$route/{$tvShowIdArg}"
    val arguments = listOf(navArgument(tvShowIdArg) { type = NavType.IntType })
}
