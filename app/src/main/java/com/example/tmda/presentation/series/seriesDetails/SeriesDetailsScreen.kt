package com.example.tmda.presentation.series.seriesDetails

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitIO.tvshowcomponent.domain.entity.Cast
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.example.tmda.R
import com.example.tmda.presentation.series.seriesHome.TvShowInfo
import com.example.tmda.presentation.shared.AppToolBar
import com.example.tmda.presentation.shared.BackGround
import com.example.tmda.ui.theme.BlackTransparent37
import com.example.tmda.ui.theme.BlackTransparent60
import com.example.tmda.ui.theme.GoldenYellow
import com.example.tmda.ui.theme.PineGreen
import com.example.tmda.ui.theme.WhiteTransparent60


@Composable
fun SeriesDetailsScreen(
    tvShowId: Int,
    modifier: Modifier = Modifier,
    viewModel: SeriesDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = tvShowId) {
        viewModel.getTvShowDetails(tvShowId)
        viewModel.getTvShowCast(tvShowId)
        viewModel.getSimilarTvShows(tvShowId)

    }
    val detailsUiState by viewModel.detailsUiState.collectAsState()
    SeriesDetailsScreen(detailsUiState, modifier)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeriesDetailsScreen(detailsUiState: DetailsUiState, modifier: Modifier) {
    val scrollState = rememberLazyListState()
    val progress = calculateProgress(scrollState)
    Box(modifier = modifier) {
        BackGround()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent), state = scrollState
        ) {
            stickyHeader {
                MotionLayoutAppBar(
                    progress = progress.value,
                    imageUrl = detailsUiState.tvShowDetails.imageURL,
                    voteCount = detailsUiState.tvShowDetails.voteCount,
                    voteAvg = detailsUiState.tvShowDetails.voteAverage
                )
            }
            item { PreviewSection(detailsUiState.tvShowDetails) }
            item { CastsRow(items = detailsUiState.cast) {} }
            item { SimilarShowsRow(items = detailsUiState.similarTvShows) {} }
        }

    }
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
private fun calculateProgress(scrollState: LazyListState): State<Float> {
    val targetValue =
        when {
            scrollState.firstVisibleItemIndex != 0 -> 1f
            else -> if (scrollState.firstVisibleItemScrollOffset > 50f) 1f else 0f
        }

    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(2000, easing = LinearOutSlowInEasing), label = ""
    )
}


@Composable
fun PreviewSection(tvShowDetails: TvShowDetails) {
    val genres = StringBuilder()
    val gen = tvShowDetails.genres.take(2)
    gen.forEach {
        if (it != gen.last()) {
            genres.append(it.name).append("/")
        } else {
            genres.append(it.name)
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp)
        )
        Text(text = tvShowDetails.title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = genres.toString(), style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            DetailsColumn(title = "Year", content = tvShowDetails.date)
            DetailsColumn(title = "Country", content = tvShowDetails.originCountry)
            DetailsColumn(title = "From", content = tvShowDetails.network)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(horizontal = 52.dp),
            text = tvShowDetails.overview,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp)
        )
    }
}

@Composable
fun DetailsColumn(title: String, content: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        Text(text = content, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun CastsRow(items: List<Cast>, onSeeAllClicked: () -> Unit) {
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
                    .width(5.dp), thickness = 1.dp, color = PineGreen
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Casts", style = MaterialTheme.typography.titleMedium)
        }
        TextButton(onClick = { /*TODO*/ }, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = "See All",
                color = PineGreen,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }

    LazyRow {
        items(items) {
            ActorCard(it)
        }

    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp)
    )
}

@Composable
fun ActorCard(cast: Cast) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(100.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cast.photo)
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "actor image"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(color = BlackTransparent60)
                .padding(8.dp)
        ) {
            Text(text = cast.name!!, fontSize = 10.sp)
            Text(text = cast.character!!, fontSize = 10.sp, color = WhiteTransparent60)

        }
    }

}

@Composable
fun SimilarShowsRow(items: List<TvShowInfo>, onSeeAllClicked: () -> Unit) {

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
                    .width(5.dp), thickness = 1.dp, color = PineGreen
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "More like this", style = MaterialTheme.typography.titleMedium)
        }
        TextButton(onClick = { /*TODO*/ }, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = "See All",
                color = PineGreen,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }

    LazyRow {
        items(items) {
            SimilarShowCard(it)
        }
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp)
    )
}

@Composable
fun SimilarShowCard(tvShowInfo: TvShowInfo) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(100.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(tvShowInfo.tvShow?.posterPath ?: tvShowInfo.tvShow?.backdropPath)
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "similar show image"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(color = BlackTransparent60)
                .padding(8.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    tint = GoldenYellow,
                    contentDescription = null
                )
                Text(text = "${tvShowInfo.tvShow?.voteAverage}")
            }
            Text(text = "${tvShowInfo.tvShow?.name}", fontSize = 10.sp)
            Text(
                text = "${tvShowInfo.tvShow?.releaseDate?.take(4)}  S${tvShowInfo.tvShowDetails?.lastSeason?.toString()}  ${tvShowInfo.tvShowDetails?.lastEpisode?.toString()} episodes",
                fontSize = 8.sp, color = WhiteTransparent60
            )

        }
    }

}


@Composable
fun UserReviews(items: List<Cast>, onSeeAllClicked: () -> Unit) {
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
                    .width(5.dp), thickness = 1.dp, color = PineGreen
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "User reviews", style = MaterialTheme.typography.titleMedium)
        }
        TextButton(onClick = { /*TODO*/ }, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = "See All",
                color = PineGreen,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }

    LazyRow {

        items(items) {
            ActorCard(it)
        }

    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp)
    )
}


@SuppressLint("ModifierParameter")
@Composable
fun MotionLayoutAppBar(
    modifier: Modifier = Modifier.background(Color.Transparent),
    imageUrl: String,
    voteCount: Int,
    voteAvg: Double,
    progress: Float
) {
    MotionLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        start = startConstraintSet(),
        end = endConstraintSet(),
        progress = progress
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .layoutId(MotionLayoutAppBarItem.Image)
                .background(Color.Transparent)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 120.dp,
                        bottomStart = 120.dp
                    )
                ),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        AppToolBar(modifier = Modifier.layoutId(MotionLayoutAppBarItem.MainAppBar), navController = rememberNavController()) {
            IconButton(
                modifier = Modifier
                    .background(color = BlackTransparent37, shape = RoundedCornerShape(8.dp))
                    .size(40.dp, 40.dp),
                onClick = { }, content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = "",
                        tint = WhiteTransparent60
                    )
                })
        }
        ServicesBox(
            modifier = Modifier.layoutId(MotionLayoutAppBarItem.ServicesBox),
            voteAvg,
            voteCount
        )


    }
}

@Composable
fun ServicesBox(modifier: Modifier, voteAvg: Double, voteCount: Int) {
    val sharedModifier = Modifier
        .background(Color.Transparent)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = sharedModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "rateIcon",
                tint = GoldenYellow,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$voteAvg/10")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$voteCount")
        }
        Column(
            modifier = sharedModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(onClick = { /*TODO*/ }) {

                Image(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(id = R.drawable.play_button),
                    contentDescription = "play trailer"
                )

            }

        }
        Column(
            modifier = sharedModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                painterResource(id = R.drawable.ic_bookmark),
                contentDescription = null,
                tint = PineGreen,
                modifier = Modifier.size(30.dp)
            )
            Text(text = "Add")

        }

    }

}

private fun startConstraintSet() = ConstraintSet {
    val mainAppBar = createRefFor(MotionLayoutAppBarItem.MainAppBar)
    val image = createRefFor(MotionLayoutAppBarItem.Image)
    val servicesBox = createRefFor(MotionLayoutAppBarItem.ServicesBox)

    constrain(mainAppBar) {
        top.linkTo(parent.top, 40.dp)
        start.linkTo(parent.start, 16.dp)
        end.linkTo(parent.end, 16.dp)
    }

    constrain(image) {
        this.width = Dimension.fillToConstraints
        this.height = Dimension.value(360.dp)
        top.linkTo(parent.top, 0.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }

    constrain(servicesBox) {

        width = Dimension.matchParent
        height = Dimension.wrapContent
        top.linkTo(parent.top, 320.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }
}

private fun endConstraintSet() = ConstraintSet {
    val mainAppBar = createRefFor(MotionLayoutAppBarItem.MainAppBar)
    val image = createRefFor(MotionLayoutAppBarItem.Image)
    val servicesBox = createRefFor(MotionLayoutAppBarItem.ServicesBox)

    constrain(mainAppBar) {
        width = Dimension.fillToConstraints
        top.linkTo(parent.top, 24.dp)
        start.linkTo(parent.start, 24.dp)
        end.linkTo(parent.end, 24.dp)

    }
    constrain(image) {
        alpha = 0f
        height = Dimension.value(0.dp)
        width = Dimension.fillToConstraints
        top.linkTo(parent.top, 0.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }
    constrain(servicesBox) {
        alpha = 0f
        height = Dimension.value(0.dp)


        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }
}

 enum class MotionLayoutAppBarItem {
    MainAppBar,
    Image,
    ServicesBox;
}
