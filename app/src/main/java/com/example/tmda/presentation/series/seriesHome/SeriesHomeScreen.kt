package com.example.tmda.presentation.series.seriesHome


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmda.presentation.navigation.navigateToShowsListScreen
import com.example.tmda.presentation.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.presentation.series.seriesList.SeriesScreenType
import com.example.tmda.presentation.series.uiDto.TvShowUiModel
import com.example.tmda.presentation.shared.base.BaseImageCard
import com.example.tmda.presentation.shared.base.BaseLazyRowComponent
import com.example.tmda.presentation.shared.base.home.BaseHomeCard
import com.example.tmda.presentation.shared.base.home.BaseNowPlayingCard
import com.example.tmda.presentation.shared.base.home.BaseNowPlayingHeader
import com.example.tmda.presentation.shared.base.mainShape
import com.example.tmda.presentation.shared.uiStates.ErrorComponent
import com.example.tmda.presentation.shared.uiStates.ErrorScreen
import com.example.tmda.presentation.shared.uiStates.LoadingScreen
import com.example.tmda.presentation.shared.uiStates.UiState

@Composable
fun SeriesHomeScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<SeriesHomeViewModel>()
    when (viewModel.isErrorState()) {
        true -> ErrorScreen(viewModel::updateAll)
        false -> LazyColumn {
            item {
                when (val nowPlaying = viewModel.trendingUiState.value) {
                    is UiState.Failure -> ErrorComponent {}
                    is UiState.Loading -> LoadingScreen(Modifier.height(400.dp))
                    is UiState.Success -> {
                        NowPlayingHeader(nowPlaying.data)
                    }
                }

            }

            item {
                HomeLazyRow(
                    title = "Popular",
                    itemsState = viewModel.popularUiState.value,
                    onSeeAllClicked = { navController.navigateToShowsListScreen(SeriesScreenType.Popular) },
                    onCardItemClicked = navController::navigateToTvShowDetailsScreen
                )
//
            }
            item {
                HomeLazyRow(
                    title = "On The Air",
                    itemsState = viewModel.onTheAirUiState.value,
                    onSeeAllClicked = { navController.navigateToShowsListScreen(SeriesScreenType.OnTheAir) },
                    onCardItemClicked = navController::navigateToTvShowDetailsScreen
                )
            }
            item {
                HomeLazyRow(
                    title = "Top Rated",
                    itemsState = viewModel.topRatedUiState.value,
                    hasBottomDivider = false,
                    onSeeAllClicked = { navController.navigateToShowsListScreen(SeriesScreenType.TopRated) },
                    onCardItemClicked = navController::navigateToTvShowDetailsScreen
                )
            }
        }

    }

}


@Composable
fun NowPlayingHeader(tvShows: List<TvShowUiModel>) {
    BaseNowPlayingHeader(items = tvShows) {
        NowPlayingCard(tvShow = tvShows[it])
    }
}

@Composable
fun NowPlayingCard(tvShow: TvShowUiModel) {
    BaseNowPlayingCard(
        title = tvShow.title,
        posterPath = tvShow.backdropPath,
        voteAverage = tvShow.voteAverage,
        voteCount = tvShow.voteCount
    )

}

@Composable
fun SeriesHomeCard(tvShow: TvShowUiModel, onTvShowClick: (Int) -> Unit) {
    BaseHomeCard(
        id = tvShow.id,
        title = tvShow.title,
        date = tvShow.releaseDate,
        genres = tvShow.genres,
        voteAverage = tvShow.voteAverage,
        posterPath = tvShow.posterPath,
        backdropPath = tvShow.backdropPath,
        onClick = onTvShowClick
    ) {
        BaseImageCard(
            imagePath = tvShow.posterPath,
            title = tvShow.title,
            modifier = HomeCardModifier
        )
    }

}



val HomeCardModifier = Modifier
    .size(200.dp, 270.dp)
    .clip(mainShape)

@Composable
fun HomeLazyRow(
    title: String,
    hasBottomDivider: Boolean = true,
    itemsState: UiState<List<TvShowUiModel>>,
    onSeeAllClicked: () -> Unit,
    onCardItemClicked: (Int) -> Unit
) {
    BaseLazyRowComponent(
        onSeeAllClicked = onSeeAllClicked,
        title=title,
        itemsState = itemsState,
        onItemClicked = onCardItemClicked,
        hasBottomDivider = hasBottomDivider
    ) { show, onItemClicked ->
        SeriesHomeCard(tvShow = show, onTvShowClick = onItemClicked)
    }
}