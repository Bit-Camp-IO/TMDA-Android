package com.example.moviesfeature.moviesHome.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesComponent.domain.enities.movie.Movie
import com.example.sharedui.base.home.BaseNowPlayingCard
import com.example.sharedui.base.home.BaseNowPlayingHeader
import com.example.sharedui.uiStates.ErrorScreen
import com.example.sharedui.uiStates.LoadingScreen
import com.example.sharedui.uiStates.UiState

@Composable
fun NowPlayingHeader(moviesUiState: UiState<List<Movie>>, onMovieClicked: (Int) -> Unit) {
    when (moviesUiState) {
        is UiState.Failure -> {
            ErrorScreen {}
        }

        is UiState.Loading -> {
            LoadingScreen(Modifier.height(416.dp))
        }

        is UiState.Success -> {
            val moviesList = moviesUiState.data
            BaseNowPlayingHeader(items = moviesList) {
                NowPlayingCard(movie = moviesList[it],onMovieClicked)
            }
        }
    }
}

@Composable
fun NowPlayingCard(movie: Movie,onMovieClicked: (Int) -> Unit) {
    BaseNowPlayingCard(
        title = movie.title,
        id=movie.id,
        posterPath = movie.posterPath,
        voteAverage = movie.voteAverage,
        voteCount = movie.voteCount,
        onClick = onMovieClicked

    )
}



