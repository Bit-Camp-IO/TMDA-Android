package com.example.tmda.presentation.series.seriesHome

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tmda.presentation.series.uiDto.TvShowUiModel
import com.example.tmda.presentation.shared.base.BaseImageCard
import com.example.tmda.presentation.shared.base.BaseLazyRowComponent
import com.example.tmda.presentation.shared.base.home.BaseHomeCard
import com.example.tmda.presentation.shared.base.home.BaseNowPlayingCard
import com.example.tmda.presentation.shared.base.home.BaseNowPlayingHeader
import com.example.tmda.presentation.shared.base.mainShape
import com.example.tmda.presentation.shared.uiStates.ErrorComponent
import com.example.tmda.presentation.shared.uiStates.LoadingScreen
import com.example.tmda.presentation.shared.uiStates.UiState

//
@Composable
fun NowPlayingHeader(tvShows: UiState<List<TvShowUiModel>>,onCardItemClicked: (Int) -> Unit) {
    when (tvShows) {
        is UiState.Failure -> ErrorComponent {}
        is UiState.Loading -> LoadingScreen(Modifier.height(400.dp))
        is UiState.Success -> {
            val series = tvShows.data
            BaseNowPlayingHeader(items = series) { NowPlayingCard(tvShow = series[it],onCardItemClicked) }
        }
    }

}

@Composable
fun NowPlayingCard(tvShow: TvShowUiModel,onCardItemClicked: (Int) -> Unit) {
    BaseNowPlayingCard(
        title = tvShow.title,
        id=tvShow.id,
        posterPath = tvShow.backdropPath,
        voteAverage = tvShow.voteAverage,
        voteCount = tvShow.voteCount,
        onClick =onCardItemClicked
    )

}

//

@Composable
fun SeriesHomeLazyRow(
    title: String,
    hasBottomDivider: Boolean = true,
    itemsState: UiState<List<TvShowUiModel>>,
    onSeeAllClicked: () -> Unit,
    onCardItemClicked: (Int) -> Unit
) {
    BaseLazyRowComponent(
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

//