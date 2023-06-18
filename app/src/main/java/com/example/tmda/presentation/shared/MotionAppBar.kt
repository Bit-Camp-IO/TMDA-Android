package com.example.tmda.presentation.shared

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movies.domain.enities.Video
import com.example.movies.domain.enities.movie.MovieDetails
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.shared.uiStates.LoadingScreen
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.ui.theme.BlackTransparent37
import com.example.tmda.ui.theme.GoldenYellow
import com.example.tmda.ui.theme.WhiteTransparent60
import kotlin.math.roundToInt

@SuppressLint("ModifierParameter")
@Composable
fun MotionLayoutAppBar(
    movieDetailsState: UiState<MovieDetails>,
    videosState: UiState<List<Video>>,
    modifier: Modifier = Modifier.background(Color.Transparent),
    progress: Float,
    isSavedState: UiState<Boolean>,
    navController: NavController,
    onSavedClicked: () -> Unit
) {
    when (movieDetailsState) {
        is UiState.Failure -> {}
        is UiState.Loading -> { LoadingScreen(modifier= Modifier.height(400.dp)) }
        is UiState.Success -> {
            val movieDetails = movieDetailsState.data
            MotionLayout(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                start = startConstraintSet(),
                end = endConstraintSet(),
                progress = progress
            ) {
                AsyncImage(
                    modifier = Modifier
                        .layoutId(MotionLayoutAppBarItem.Image)
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 24.dp,
                                bottomStart = 24.dp
                            )
                        ),
                    model = getTmdbImageLink(movieDetails.posterPath),
                    contentScale = ContentScale.Crop,
                    contentDescription = "${movieDetails.title} image",
                    alignment = Alignment.TopStart
                )
                AppToolBar(modifier = Modifier.layoutId(MotionLayoutAppBarItem.MainAppBar), navController =navController) {
                    IconButton(
                        modifier = Modifier
                            .background(
                                color = BlackTransparent37,
                                shape = RoundedCornerShape(8.dp)
                            )
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
                    movieDetails,
                    videosState,
                    isSavedState,
                    onSavedClicked
                )


            }
        }
    }

}

@Composable
fun ServicesBox(
    modifier: Modifier,
    movieDetails: MovieDetails,
    videosState: UiState<List<Video>>,
    isSavedState: UiState<Boolean>,
    onSavedClicked: () -> Unit
) {
    val context = LocalContext.current as ComponentActivity
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
            Text(text = movieDetails.voteAverage.roundToInt().toString() + "/10")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movieDetails.voteCount.toString())
        }
        Column(
            modifier = sharedModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (val videosState = videosState) {
                is UiState.Failure -> TODO()
                is UiState.Loading -> {
                    LoadingScreen()
                }

                is UiState.Success -> {

                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                val videoKeys = videosState.data.getParsedYoutubeList()
                                context.openYouTubePlaylist(videoKeys)
                            },
                        painter = painterResource(id = R.drawable.play_button),
                        contentDescription = "play trailer"
                    )
                }
            }

        }
        Column(
            modifier = sharedModifier.clickable { onSavedClicked() },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            SavedItemIcon(modifier = Modifier.size(30.dp), isSavedState = isSavedState)
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
        this.height = Dimension.value(370.dp)
        top.linkTo(parent.top, 0.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

    }

    constrain(servicesBox) {

        width = Dimension.matchParent
        height = Dimension.wrapContent
        top.linkTo(parent.top, 330.dp)
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

private enum class MotionLayoutAppBarItem {
    MainAppBar,
    Image,
    ServicesBox;
}

fun List<Video>.getParsedYoutubeList() =
    filter { it.site == "YouTube" && it.type == "Trailer" }
        .joinToString(separator = ",") { it.key }


fun Activity.openYouTubePlaylist(videoKeys: String) {
    val playlistUrl = "https://www.youtube.com/watch_videos?video_ids=$videoKeys"

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playlistUrl))
    intent.setPackage("com.google.android.youtube")

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(playlistUrl)))
    }
}
