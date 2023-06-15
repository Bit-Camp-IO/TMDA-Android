package com.example.tmda.presentation.movies.moviesHome

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmda.presentation.movies.moviesHome.components.MovieHomeCard
import com.example.tmda.presentation.movies.moviesHome.components.NowPlayingHeader
import com.example.tmda.presentation.movies.moviesList.MoviesScreenType
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.navigation.navigateToMovieListScreen
import com.example.tmda.presentation.shared.ErrorScreen
import com.example.tmda.presentation.shared.ItemsLazyRowComponent


@Composable
fun MoviesHomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<MoviesHomeViewModel>()
    if (viewModel.isErrorState()) ErrorScreen(viewModel::updateAll) else
        LazyColumn {
            item { NowPlayingHeader(viewModel.nowPlayingMoviesState.value) }
            item {
                ItemsLazyRowComponent(
                    title = "Popular",
                    onSeeAllClicked = {
                        navController.navigateToMovieListScreen(
                            "Popular Movies",
                            MoviesScreenType.Popular
                        )
                    },
                    moviesUiState = viewModel.popularMoviesState.value
                ) {
                    MovieHomeCard(
                        movie = it,
                        navController::navigateToMovieDetails,
                    )
                }
            }
            item {
                ItemsLazyRowComponent(
                    title = "Upcoming",
                    onSeeAllClicked = {
                        navController.navigateToMovieListScreen(
                            "Upcoming Movies",
                            MoviesScreenType.Upcoming
                        )
                    },
                    moviesUiState = viewModel.upComingMoviesState.value
                ) {
                    MovieHomeCard(
                        movie = it,
                        onClick = navController::navigateToMovieDetails,

                    )
                }
            }
            item {
                ItemsLazyRowComponent(
                    title = "Top Rated",
                    hasBottomDivider = false,
                    onSeeAllClicked = {
                        navController.navigateToMovieListScreen(
                            "Top Rated Movies",
                            MoviesScreenType.TopRated
                        )
                    },
                    moviesUiState = viewModel.topRatedMoviesState.value
                ) {
                    MovieHomeCard(
                        movie = it,
                        onClick = navController::navigateToMovieDetails,

                    )
                }
            }
        }
}