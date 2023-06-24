package com.example.tvshowfeature.seriesHome

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sharedui.x.imageCardModifier
import com.example.tvshowfeature.uiDto.TvShowUiModel

@Composable
fun NowPlayingHeader(tvShows: com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>, onCardItemClicked: (Int) -> Unit) {
    when (tvShows) {
        is com.example.sharedui.uiStates.UiState.Failure -> com.example.sharedui.uiStates.ErrorComponent {}
        is com.example.sharedui.uiStates.UiState.Loading -> com.example.sharedui.uiStates.LoadingScreen(
            Modifier.height(400.dp)
        )
        is com.example.sharedui.uiStates.UiState.Success -> {
            val series = tvShows.data
            com.example.sharedui.base.home.BaseNowPlayingHeader(items = series) {
                NowPlayingCard(
                    tvShow = series[it],
                    onCardItemClicked
                )
            }
        }
    }

}

@Composable
fun NowPlayingCard(tvShow: TvShowUiModel, onCardItemClicked: (Int) -> Unit) {
    com.example.sharedui.base.home.BaseNowPlayingCard(
        title = tvShow.title,
        id = tvShow.id,
        posterPath = tvShow.backdropPath,
        voteAverage = tvShow.voteAverage,
        voteCount = tvShow.voteCount,
        onClick = onCardItemClicked
    )

}


@Composable
fun SeriesHomeLazyRow(
    title: String,
    hasBottomDivider: Boolean = true,
    itemsState: com.example.sharedui.uiStates.UiState<List<TvShowUiModel>>,
    onSeeAllClicked: () -> Unit,
    onCardItemClicked: (Int) -> Unit
) {
    com.example.sharedui.base.BaseLazyRowComponent(
        onSeeAllClicked = onSeeAllClicked,
        title = title,
        itemsState = itemsState,
        onItemClicked = onCardItemClicked,
        hasBottomDivider = hasBottomDivider
    ) { show, onItemClicked ->
        SeriesHomeCard(tvShow = show, onTvShowClick = onItemClicked)
    }
}

@Composable
fun SeriesHomeCard(tvShow: TvShowUiModel, onTvShowClick: (Int) -> Unit) {
    com.example.sharedui.base.home.BaseHomeCard(
        id = tvShow.id,
        title = tvShow.title,
        date = tvShow.releaseDate,
        genres = tvShow.genres,
        voteAverage = tvShow.voteAverage,
        posterPath = tvShow.posterPath,
        backdropPath = tvShow.backdropPath,
        onClick = onTvShowClick
    ) {
        com.example.sharedui.base.BaseImageCard(
            imagePath = tvShow.posterPath,
            title = tvShow.title,
            modifier = imageCardModifier
        )
    }

}
