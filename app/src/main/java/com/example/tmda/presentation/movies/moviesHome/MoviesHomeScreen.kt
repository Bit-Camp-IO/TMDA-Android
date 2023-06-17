package com.example.tmda.presentation.movies.moviesHome

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmda.presentation.movies.moviesHome.components.MovieHomeCard
import com.example.tmda.presentation.movies.moviesHome.components.NowPlayingHeader
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import com.example.tmda.presentation.movies.moviesList.MoviesScreenType
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.navigation.navigateToMovieListScreen
import com.example.tmda.presentation.shared.UiStates.ErrorScreen
import com.example.tmda.presentation.shared.UiStates.UiState
import com.example.tmda.presentation.shared.base.BaseLazyRowComponent


@Composable
fun MoviesHomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<MoviesHomeViewModel>()
    if (viewModel.isErrorState()) ErrorScreen(viewModel::updateAll) else
        LazyColumn {
            item { NowPlayingHeader(viewModel.nowPlayingMoviesState.value) }
            item {
                HomeLazyRow(
                    title = "Popular",
                    onSeeAllClicked = {
                        navController.navigateToMovieListScreen(
                            "Popular Movies",
                            MoviesScreenType.Popular
                        )
                    },
                    itemsState = viewModel.popularMoviesState.value,
                    onCardItemClicked = navController::navigateToMovieDetails
                )
            }
            item {
                HomeLazyRow(
                    title = "Upcoming",
                    onSeeAllClicked = {
                        navController.navigateToMovieListScreen(
                            "Upcoming Movies",
                            MoviesScreenType.Upcoming
                        )
                    },
                    itemsState = viewModel.upComingMoviesState.value,
                    onCardItemClicked = navController::navigateToMovieDetails
                )
            }
            item {
                HomeLazyRow(
                    title = "Top Rated",
                    hasBottomDivider = false,
                    onSeeAllClicked = {
                        navController.navigateToMovieListScreen(
                            "Top Rated Movies",
                            MoviesScreenType.TopRated
                        )
                    },
                    itemsState = viewModel.topRatedMoviesState.value,
                    onCardItemClicked = navController::navigateToMovieDetails
                )

            }
        }
}

@Composable
fun HomeLazyRow(
    title: String,
    hasBottomDivider: Boolean = true,
    itemsState: UiState<List<MovieUiDto>>,
    onSeeAllClicked: () -> Unit,
    onCardItemClicked: (Int) -> Unit
) {
    BaseLazyRowComponent(
        title = title,
        hasBottomDivider = hasBottomDivider,
        onSeeAllClicked = onSeeAllClicked,
        onItemClicked = onCardItemClicked,
        itemsState = itemsState
    ) { data, onItemClicked ->
        MovieHomeCard(movie = data, onClick = onItemClicked)
    }
}