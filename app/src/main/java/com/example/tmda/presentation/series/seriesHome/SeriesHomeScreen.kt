package com.example.tmda.presentation.series.seriesHome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tmda.R
import com.example.tmda.presentation.shared.ImageCard
import com.example.tmda.presentation.shared.ItemsLazyRowComponent
import com.example.tmda.ui.theme.WhiteTransparent60
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun SeriesHomeScreen(viewModel: SeriesHomeViewModel = hiltViewModel(), onSeeAllClick: (Int) -> Unit,
                     onTvShowClick: (Int) -> Unit) {
    val seriesUiState by viewModel.seriesUiState.collectAsState()

    SeriesHomeScreen(seriesUiState, onSeeAllClick = {
        onSeeAllClick(it)

    }, onTvShowClick = {
        onTvShowClick(it)
    })
}

@Composable
fun SeriesHomeScreen(
    seriesUiState: SeriesUiState,
    modifier: Modifier = Modifier,
    onSeeAllClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit
) {
    LazyColumn(modifier) {
        item {
            if (seriesUiState.isNowPlayingLoading) {
                Box(Modifier.size(400.dp)) {
                    CircularProgressIndicator(
                        modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            if (seriesUiState.nowPlayingErrorMsg != null) {
                Column(
                    Modifier.size(400.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error icon"
                    )
                    Text(
                        text = seriesUiState.nowPlayingErrorMsg!!,
                        textAlign = TextAlign.Center
                    )
                }
            }
            NowPlayingHeader(seriesUiState.tvShowInfo)
        }
        item {
            ItemsLazyRowComponent(
                title = "Popular",
                items = seriesUiState.popularTvShows,
                onSeeAllClicked = { onSeeAllClick(0) }
            ) {
                SeriesHomeCard(tvShowInfo = it, onTvShowClick = { onTvShowClick(it) })
            }
        }
        item {
            ItemsLazyRowComponent(
                title = "On The Air",
                items = seriesUiState.onTheAirTvShows,
                onSeeAllClicked = { onSeeAllClick(1) }
            ) {
                SeriesHomeCard(tvShowInfo = it, onTvShowClick = { onTvShowClick(it) })
            }
        }
        item {
            ItemsLazyRowComponent(
                title = "Top Rated",
                hasBottomDivider = false,
                items = seriesUiState.topRatedTvShows,
                onSeeAllClicked = { onSeeAllClick(2) }
            ) {
                SeriesHomeCard(tvShowInfo = it!!, onTvShowClick = { onTvShowClick(it) })
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NowPlayingHeader(tvShows: List<TvShowInfo>) {
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
fun NowPlayingCard(tvShow: TvShowInfo) {
    val date = tvShow.tvShow?.releaseDate?.let { "$it . " }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(tvShow.tvShow?.backdropPath ?: tvShow.tvShow?.posterPath)
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Series poster",
        )
        Column(modifier = Modifier.padding(start = 24.dp)) {
            Text(
                text = tvShow.tvShow?.name!!,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
            )
            Row {
                Text(
                    text = date + tvShow.tvShow.genres?.get(0)?.name,
                    color = WhiteTransparent60
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "S${tvShow.tvShowDetails?.lastSeason?.toString()} ${tvShow.tvShowDetails?.lastEpisode?.toString()} Episodes",
                    color = WhiteTransparent60
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TotalRatingIcons(rating = tvShow.tvShow.voteAverage.toFloat(), iconSize = 14.dp)
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
fun SeriesHomeCard(tvShowInfo: TvShowInfo, onTvShowClick: (Int) -> Unit) {
    val genres = StringBuilder()
    val gen = tvShowInfo.tvShow?.genres?.take(2)
    gen?.forEach {
        if (it != null) {
            if (it != gen.last()) {
                genres.append(it.name).append("/")
            } else {
                genres.append(it.name)
            }
        }
    }
    val subTitle =
        tvShowInfo.tvShow?.releaseDate?.let { "${it.take(4)} . " } + genres +
                tvShowInfo.tvShowDetails?.lastSeason?.let { "\nS$it  " } +
                tvShowInfo.tvShowDetails?.lastEpisode?.let { "$it Episodes" }
    Box(contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.clickable(MutableInteractionSource(), null) { onTvShowClick(tvShowInfo.tvShow?.id!!) }) {
        ImageCard(tvShowInfo.tvShow?.backdropPath ?: tvShowInfo.tvShow?.posterPath,"",200.dp,200.dp)
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = tvShowInfo.tvShow?.name!!,
                Modifier.padding(end = 24.dp),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = subTitle,
                style = MaterialTheme.typography.labelSmall
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tvShowInfo.tvShow.voteAverage.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                TotalRatingIcons(rating = tvShowInfo.tvShow.voteAverage.toFloat(), iconSize = 16.dp)
            }
        }
    }
}
