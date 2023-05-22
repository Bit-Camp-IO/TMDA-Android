package com.example.tmda

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val ui=viewModel.uiState.collectAsState()
    
    Text(text = "Test ViewModel ${ui.value}")
}