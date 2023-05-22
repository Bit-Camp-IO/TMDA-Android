package com.bitIO.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bitIO.ui.screens.tvshow.tvShowRoute


const val START_DESTINATION = "HOME_SCREEN"

@Composable
fun TvShowNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        tvShowRoute(navController)
    }
}