package com.example.moviesfeature.moviesHome

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesfeature.moviesHome.components.MovieHomeCard
import com.example.moviesfeature.moviesHome.components.NowPlayingHeader
import com.example.moviesfeature.navigation.MoviesNavigator
import com.example.moviesfeature.uiModels.MovieUiDto
import com.example.moviesfeature.uiModels.MoviesScreenType
import com.example.sharedui.base.BaseLazyRowComponent
import com.example.sharedui.uiStates.ErrorScreen
import com.example.sharedui.uiStates.UiState


@Composable
fun MoviesHomeScreen(moviesNavigator: MoviesNavigator) {
    val viewModel = hiltViewModel<MoviesHomeViewModel>()
    if (viewModel.isErrorState()) ErrorScreen(viewModel::updateAll) else
        LazyColumn {
            item {
                NowPlayingHeader(
                    viewModel.nowPlayingMoviesState.value,
                    moviesNavigator::navigateToMovieDetails
                )
            }
            item {
                HomeLazyRow(
                    title = "Popular",
                    onSeeAllClicked = {
                        moviesNavigator.navigateToMovieListScreen(
                            "Popular Movies",
                            MoviesScreenType.Popular
                        )
                    },
                    itemsState = viewModel.popularMoviesState.value,
                    onCardItemClicked = moviesNavigator::navigateToMovieDetails
                )
            }
            item {
                HomeLazyRow(
                    title = "Upcoming",
                    onSeeAllClicked = {
                        moviesNavigator.navigateToMovieListScreen(
                            "Upcoming Movies",
                            MoviesScreenType.Upcoming
                        )
                    },
                    itemsState = viewModel.upComingMoviesState.value,
                    onCardItemClicked = moviesNavigator::navigateToMovieDetails
                )
            }
            item {
                HomeLazyRow(
                    title = "Top Rated",
                    hasBottomDivider = false,
                    onSeeAllClicked = {
                        moviesNavigator.navigateToMovieListScreen(
                            "Top Rated Movies",
                            MoviesScreenType.TopRated
                        )
                    },
                    itemsState = viewModel.topRatedMoviesState.value,
                    onCardItemClicked = moviesNavigator::navigateToMovieDetails
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