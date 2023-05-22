package com.bitIO.ui.screens.tvshow

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun TvShowScreen(
    navController: NavController,
    viewModel: TvShowViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Text("Test API: ${uiState.result} ")

}