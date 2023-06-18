package com.example.tmda.presentation.movies.moviesHome

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmda.presentation.movies.moviesHome.components.MovieHomeCard
import com.example.tmda.presentation.movies.moviesHome.components.NowPlayingHeader
import com.example.tmda.presentation.movies.uiModels.MovieUiDto
import com.example.tmda.presentation.movies.uiModels.MoviesScreenType
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.navigation.navigateToMovieListScreen
import com.example.tmda.presentation.shared.base.BaseLazyRowComponent
import com.example.tmda.presentation.shared.uiStates.ErrorScreen
import com.example.tmda.presentation.shared.uiStates.UiState


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