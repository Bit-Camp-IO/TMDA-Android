package com.bitIO.ui.screens.tvshow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bitIO.ui.START_DESTINATION


private const val ROUTE = START_DESTINATION
fun NavGraphBuilder.tvShowRoute(navController: NavHostController) {
    composable(route = ROUTE) {
        TvShowScreen()
    }
}