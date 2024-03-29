package com.example.tvshowfeature.seriesDetails

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.sharedComponent.entities.Video
import com.example.sharedui.R
import com.example.sharedui.base.credits.CreditsComponent
import com.example.sharedui.base.imageCardModifier
import com.example.sharedui.base.mainShape
import com.example.sharedui.colors.BlackTransparent37
import com.example.sharedui.colors.GoldenYellow
import com.example.sharedui.colors.WhiteTransparent60
import com.example.sharedui.uiStates.toSuccessState
import com.example.sharedui.utils.getParsedYoutubeList
import com.example.sharedui.utils.getTmdbImageLink
import com.example.sharedui.utils.openYouTubePlaylist
import com.example.sharedui.x.AppToolBar
import com.example.sharedui.x.BackGround
import com.example.sharedui.x.SavedItemIcon
import com.example.tvshowfeature.navigation.TvShowNavigator
import com.example.tvshowfeature.seriesDetails.uiDto.OverView
import com.example.tvshowfeature.seriesList.SeriesScreenType
import kotlin.math.roundToInt

val shape = mainShape
@Composable
fun SeriesDetailsScreen(
    tvShowNavigator: TvShowNavigator,
    modifier: Modifier = Modifier,

    ) {
    val viewModel = hiltViewModel<SeriesDetailsViewModel>()
    SeriesDetailsScreen(
        viewModel.overView.value,
        modifier,
        tvShowNavigator,
        viewModel.seriesId,
        viewModel::updateOverView,
        viewModel::addOrRemoveSeriesFromSaveList
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeriesDetailsScreen(
    overViewState: com.example.sharedui.uiStates.UiState<OverView>,
    modifier: Modifier,
    tvShowNavigator: TvShowNavigator,
    seriesId: Int,
    onTryAgain: () -> Unit,
    onSaveClicked: () -> Unit
) {
    when (overViewState) {
        is com.example.sharedui.uiStates.UiState.Failure -> com.example.sharedui.uiStates.ErrorScreen(
            onTryAgain
        )
        is com.example.sharedui.uiStates.UiState.Loading -> com.example.sharedui.uiStates.LoadingScreen()
        is com.example.sharedui.uiStates.UiState.Success -> {
            val overView = overViewState.data
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
                            imageUrl = overView.image,
                            rating = overView.rating,
                            totalVotes = overView.voteCount,
                            videos = overView.videos,
                            savedState = overView.savedState,
                            onSaveClicked = onSaveClicked,
                            tvShowNavigator = tvShowNavigator

                        )
                    }
                    item { PreviewSection(overView) }
                    item {
                        CreditsComponent(
                            creditItemsState = overView.cast.toSuccessState(),
                            onCardClicked = tvShowNavigator::navigateToPersonScreen
                        )
                    }
                    item {
                        SimilarSeriesRow(
                            title = "Similar",
                            seriesState = overView.similarSeries.toSuccessState(),
                            onCardItemClicked = tvShowNavigator::navigateToTvShowDetailsScreen
                        ) {
                            tvShowNavigator.navigateToShowsListScreen(
                                SeriesScreenType.Similar,
                                seriesId
                            )
                        }
                    }
                    item {
                        SimilarSeriesRow(
                            title = "Related",
                            seriesState = overView.recommendedSeries.toSuccessState(),
                            onCardItemClicked = tvShowNavigator::navigateToTvShowDetailsScreen
                        ) {
                            tvShowNavigator.navigateToShowsListScreen(
                                SeriesScreenType.Similar,
                                seriesId
                            )
                        }
                    }
                    item { com.example.sharedui.reviews.ReviewsList(reviews = overView.reviews.toSuccessState()) }
                }

            }
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
fun PreviewSection(overView: OverView) {


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp)
        )
        Text(text = overView.title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = overView.genres, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            DetailsColumn(title = "Year", content = overView.releaseYear)
            DetailsColumn(title = "Country", content = overView.country)
            DetailsColumn(title = "From", content = overView.company)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(horizontal = 52.dp),
            text = overView.overView,
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
fun SimilarSeriesRow(
    title: String = "More like this",
    seriesState: com.example.sharedui.uiStates.UiState<List<TvShow>>,
    hasSeeAll: Boolean = true,
    onCardItemClicked: (Int) -> Unit,
    onSeeAllClicked: () -> Unit
) {
    com.example.sharedui.base.BaseLazyRowComponent(
        title = title,
        onSeeAllClicked = onSeeAllClicked,
        hasSeeAll = hasSeeAll,
        itemsState = seriesState,
        onItemClicked = onCardItemClicked
    ) { tvShow, _ ->
        SimilarTvShowCard(tvShow = tvShow, onCardItemClicked = onCardItemClicked)
    }
}

@Composable
fun SimilarTvShowCard(tvShow: TvShow, onCardItemClicked: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(180.dp)
            .clip(shape)
            .clickable { onCardItemClicked(tvShow.id) },
        contentAlignment = Alignment.BottomCenter
    ) {
        com.example.sharedui.base.BaseImageCard(
            imagePath = getTmdbImageLink(
                tvShow.backdropPath ?: tvShow.posterPath
            ),
            title = tvShow.name,
            modifier = similarTvShowCardModifier
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.ic_star),
                    tint = GoldenYellow,
                    contentDescription = null
                )
                Text(text = tvShow.voteAverage.roundToInt().toString())
            }
            Text(text = tvShow.name, fontSize = 10.sp, maxLines = 1)
            Text(
                text = tvShow.voteCount.toString(),
                fontSize = 8.sp,
                color = WhiteTransparent60,
                maxLines = 1
            )

        }
    }

}

val similarTvShowCardModifier = Modifier.imageCardModifier(140.dp, 180.dp)


@SuppressLint("ModifierParameter")
@Composable
fun MotionLayoutAppBar(
    modifier: Modifier = Modifier.background(Color.Transparent),
    imageUrl: String,
    rating: Double,
    totalVotes: Int,
    progress: Float,
    tvShowNavigator: TvShowNavigator,
    videos: List<Video>,
    savedState: MutableState<Boolean>,
    onSaveClicked: () -> Unit
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
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    )
                ),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        AppToolBar(
            modifier = Modifier.layoutId(MotionLayoutAppBarItem.MainAppBar),
            navController = tvShowNavigator.navController
        ) {
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
            rating,
            totalVotes = totalVotes,
            savedState = savedState,
            onSaveClicked = onSaveClicked,
            videos
        )


    }
}

@Composable
fun ServicesBox(
    modifier: Modifier,
    voteAvg: Double,
    totalVotes: Int,
    savedState: MutableState<Boolean>,
    onSaveClicked: () -> Unit,
    videos:List<Video>

) {
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
            Text(text = "$totalVotes")
        }
        Column(
            modifier = sharedModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current as ComponentActivity
            TextButton(onClick = {
                val videoKeys = videos. getParsedYoutubeList()
                context.openYouTubePlaylist(videoKeys)
            }) {

                Image(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(id = R.drawable.play_button),
                    contentDescription = "play trailer"
                )

            }

        }
        Column(
            modifier = sharedModifier.clickable { onSaveClicked() },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            SavedItemIcon(
                modifier = Modifier.size(30.dp),
                isSavedState = savedState
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
