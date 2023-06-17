package com.example.tmda.presentation.movies.moviesHome.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movies.domain.enities.movie.Movie
import com.example.tmda.presentation.shared.UiStates.ErrorScreen
import com.example.tmda.presentation.shared.UiStates.LoadingScreen
import com.example.tmda.presentation.shared.UiStates.UiState
import com.example.tmda.presentation.shared.base.home.BaseNowPlayingCard
import com.example.tmda.presentation.shared.base.home.BaseNowPlayingHeader

@Composable
fun NowPlayingHeader(moviesUiState: UiState<List<Movie>>) {
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
                NowPlayingCard(movie = moviesList[it])
            }
        }
    }
}

@Composable
fun NowPlayingCard(movie: Movie) {
    BaseNowPlayingCard(
        title = movie.title,
        posterPath = movie.posterPath,
        voteAverage = movie.voteAverage,
        voteCount = movie.voteCount
    )
}



