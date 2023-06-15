package com.example.tmda.presentation.series.seriesHome


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.movies.moviesList.ErrorComponent
import com.example.tmda.presentation.navigation.navigateToShowsListScreen
import com.example.tmda.presentation.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.presentation.series.seriesList.SeriesScreenType
import com.example.tmda.presentation.series.uiDto.TvShowUiModel
import com.example.tmda.presentation.shared.ErrorScreen
import com.example.tmda.presentation.shared.ImageCard
import com.example.tmda.presentation.shared.LoadingScreen
import com.example.tmda.presentation.shared.UiState
import com.example.tmda.presentation.shared.imageCardModifier
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.ui.theme.PineGreenDark
import com.example.tmda.ui.theme.WhiteTransparent60
import kotlin.math.ceil
import kotlin.math.floor
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
                    ItemsLazyRowComponent(
                        title = "Popular",
                        itemsState = viewModel.popularUiState,
                        onSeeAllClicked = { navController.navigateToShowsListScreen(SeriesScreenType.Popular)}
                    ) {
                        SeriesHomeCard(tvShow = it, onTvShowClick = navController::navigateToTvShowDetailsScreen)
                    }
                }
                item {
                    ItemsLazyRowComponent(
                        title = "On The Air",
                        itemsState = viewModel.onTheAirUiState,
                        onSeeAllClicked =  { navController.navigateToShowsListScreen(SeriesScreenType.OnTheAir)}
                    ) {
                        SeriesHomeCard(tvShow = it, onTvShowClick =navController::navigateToTvShowDetailsScreen)
                    }
                }
                item {
                    ItemsLazyRowComponent(
                        title = "Top Rated",
                        hasBottomDivider = false,
                        itemsState = viewModel.topRatedUiState,
                        onSeeAllClicked =  { navController.navigateToShowsListScreen(SeriesScreenType.TopRated)}
                    ) {
                        SeriesHomeCard(tvShow = it, onTvShowClick = navController::navigateToTvShowDetailsScreen )
                    }
                }
            }

    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NowPlayingHeader(tvShows: List<TvShowUiModel>) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { tvShows.size }
    Column(Modifier.background(Color.Transparent)) {
        HorizontalPager(state = pagerState) {
            NowPlayingCard(tvShows[it])
        }
        Spacer(Modifier.height(8.dp))
        DotsIndicator(totalDots = pagerState.pageCount, currentIndex = pagerState.currentPage)

    }

}

@Composable
fun NowPlayingCard(tvShow: TvShowUiModel) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = getTmdbImageLink(tvShow.backdropPath?:tvShow.posterPath),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Series poster",
        )
        Column(modifier = Modifier.padding(start = 24.dp)) {
            Text(
                text = tvShow.title,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
            )
            Row {
                Text(
                    text = tvShow.releaseDate + "." + tvShow.genres,
                    color = WhiteTransparent60
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = tvShow.originalLanguage,
                    color = WhiteTransparent60
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TotalRatingIcons(rating = tvShow.voteAverage.toFloat(), iconSize = 14.dp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier.alpha(0f),
                text = "From 324 users",
                style = MaterialTheme.typography.bodySmall,
                color = WhiteTransparent60
            )

        }
    }
}

@Composable // TODO: could be shared
fun TotalRatingIcons(
    maxRating: Int = 10,
    rating: Float,
    starsCount: Int = 5,
    iconSize: Dp = 12.dp
) {
    val actualRating = rating * starsCount / maxRating
    val filledStars = floor(actualRating).toInt()
    val unFilledStars = starsCount - ceil(actualRating).toInt()
    val halfFilledStar = starsCount - (filledStars + unFilledStars)
    val sharedModifier = Modifier
        .size(iconSize)
        .padding(end = 8.dp)
        .size(iconSize)

    Row {
        for (i in 0 until filledStars) {
            Icon(
                modifier = sharedModifier,
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        if (halfFilledStar == 1) {
            Icon(
                modifier = sharedModifier,
                painter = painterResource(id = R.drawable.ic_half_star),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        for (i in 0 until unFilledStars) {
            Icon(
                modifier = sharedModifier,
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = Color.White
            )
        }
    }

}

@Composable // TODO: could be shared
fun DotsIndicator(totalDots: Int, currentIndex: Int) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        for (i in 0 until totalDots) {
            if (i == currentIndex)
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    painter = painterResource(id = R.drawable.ic_round_rectangle),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            else
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    painter = painterResource(id = R.drawable.ic_dot),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
        }
    }
}


@Composable
fun SeriesHomeCard(tvShow: TvShowUiModel, onTvShowClick: (Int) -> Unit) {

    Box(contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.clickable(
            MutableInteractionSource(),
            null
        ) { onTvShowClick(tvShow.id) }) {
        ImageCard(
            tvShow.backdropPath ?: tvShow.posterPath,
            tvShow.title,
            HomeCardModifier

        )
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = tvShow.title,
                Modifier.padding(end = 24.dp),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = tvShow.releaseDate + "." + tvShow.genres,
                style = MaterialTheme.typography.labelSmall
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tvShow.voteAverage.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                TotalRatingIcons(rating = tvShow.voteAverage.toFloat(), iconSize = 16.dp)
            }
        }
    }
}

@Composable
fun <T> ItemsLazyRowComponent(
    title: String = "More like this",
    hasBottomDivider: Boolean = true,
    onSeeAllClicked: () -> Unit,
    itemsState: State<UiState<List<T>>>,
    contentCard: @Composable (T) -> Unit
) {
    when (val items = itemsState.value) {
        is UiState.Failure -> ErrorComponent {}
        is UiState.Loading -> LoadingScreen(modifier = Modifier.height(360.dp))
        is UiState.Success -> {
            val data = items.data

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Divider(
                        modifier = Modifier
                            .height(20.dp)
                            .width(5.dp), thickness = 1.dp, color = PineGreenDark
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                }
                TextButton(onClick = onSeeAllClicked, contentPadding = PaddingValues(0.dp)) {
                    Text(
                        text = "See All",
                        color = PineGreenDark,
                        style = MaterialTheme.typography.titleSmall
                    )

                }
            }

            LazyRow {
                item { Spacer(modifier = Modifier.width(16.dp)) }
                items(data.size) { contentCard(data[it]) }
                item { Spacer(modifier = Modifier.width(16.dp)) }
            }
            if (hasBottomDivider) Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 32.dp)
            ) else Spacer(modifier = Modifier.height(16.dp))
        }
    }


}

val HomeCardModifier = Modifier
    .size(200.dp, 270.dp)
    .clip(mainShape)
val imageCardModifier = Modifier.imageCardModifier(200.dp, 270.dp)