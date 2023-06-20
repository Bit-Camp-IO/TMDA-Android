package com.example.tmda.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.tmda.presentation.series.seriesList.SeriesScreenType


object Destinations {
    const val MOVIES_ROUTE = "moviesRoute"
    const val MOVIES_HOME_SCREEN = "moviesHomeScreen"
    const val MOVIES_DETAILS_SCREEN = "movieDetailsScreen"
    const val MOVIES_LIST_SCREEN = "movieListScreen"
    const val PERSON_SCREEN = "PersonScreen"

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
const val MOVIES_LIST_SCREEN_TYPE = "movieListScreenType"
const val MOVIE_ID = "movieId"
const val MOVIE_LIST_SCREEN_TITLE = "movieListScreenTitle"
const val PERSON_ID="personID"
//series list screen args
const val SERIES_LIST_SCREEN_TYPE = "seriesListScreenType"
const val SERIES_ID = "SeriesId"


object TvShowsList {
    // const val route = "tvshows"
    //  const val tvShowTypeArg = "tvshow_type"
//  const val routeWithArgs = "$route/{$tvShowTypeArg}"
    val arguments = listOf(
        navArgument(SERIES_LIST_SCREEN_TYPE) {
            type = NavType.EnumType(SeriesScreenType::class.java)
        },
        navArgument(SERIES_ID) {
            type = NavType.IntType
            defaultValue = -1
        })
}

object TvShowDetails {
    private const val route = "tvshow_details"
    const val routeWithArgs = "$route/{$SERIES_ID}"
    val arguments = listOf(navArgument(SERIES_ID) { type = NavType.IntType })
}
