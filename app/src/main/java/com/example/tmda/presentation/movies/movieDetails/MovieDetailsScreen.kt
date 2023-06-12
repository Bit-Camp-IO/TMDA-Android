@file:OptIn(ExperimentalFoundationApi::class)

package com.example.tmda.presentation.movies.movieDetails

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.movies.domain.enities.Genre
import com.example.movies.domain.enities.movie.MovieDetails
import com.example.tmda.presentation.movies.CreditsComponent
import com.example.tmda.presentation.movies.moviesList.ScreenType
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.navigation.navigateToMovieListScreen
import com.example.tmda.presentation.shared.LoadingScreen
import com.example.tmda.presentation.shared.MotionLayoutAppBar
import com.example.tmda.presentation.shared.UiState


@Composable

fun MovieDetailsScreen(
    navController: NavController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val stateHolder = viewModel.detailsScreenStateHolder


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.checkIfSavedStateUpdated()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }


    DetailsScreenLoaded(
        stateHolder = stateHolder,
        viewModel::addOrRemoveMovieToSavedList,
        navController
    )


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreenLoaded(
    stateHolder: DetailsScreenStateHolder,
    onSavedClicked: () -> Unit,
    navController: NavController
) {
    val scrollState = rememberLazyListState()
    val motionLayoutState = remember {
        mutableFloatStateOf(0f)
    }
    val progress =  calculateProgress(motionLayoutState, scrollState)
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent), state = scrollState
        ) {

            stickyHeader {
                motionLayoutState.value = progress.value
                MotionLayoutAppBar(
                    progress = progress.value,
                    movieDetailsState = stateHolder.movieDetails.value,
                    isSavedState = stateHolder.isSaved.value,
                    videosState = stateHolder.movieVideos.value,
                    navController = navController,
                    onSavedClicked = onSavedClicked
                )
            }

            item { PreviewSection(movieDetailsState = stateHolder.movieDetails.value) }
            item {
                CreditsComponent(
                    title = "Cast",
                    creditItemsState = stateHolder.movieCredits.value,
                    onSeeAllClicked = {},
                    onCardClicked = {})
            }
            item {
                SimilarMoviesRow(
                    "Similar Movies",
                    stateHolder.similarMovies.value,
                    navController::navigateToMovieDetails
                ) {
                    navController.navigateToMovieListScreen(
                        "Similar Movies",
                        ScreenType.Similar,
                        stateHolder.movieId
                    )
                }
            }
            item {
                SimilarMoviesRow(
                    "Related",
                    stateHolder.recommendedMovies.value,
                    navController::navigateToMovieDetails
                ) {
                    navController.navigateToMovieListScreen(
                        "Related",
                        ScreenType.Recommended,
                        stateHolder.movieId
                    )
                }
            }
        }

    }

}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
private fun calculateProgress(
    appBarState: MutableState<Float>,
    scrollState: LazyListState
): State<Float> {

    val targetOffset = 50f
  //  var targetValue=0f
   // val isLastItemVisible = scrollState.layoutInfo.visibleItemsInfo.lastIndex==4
//    if (scrollState.isScrollingUp()){
//        if (scrollState.firstVisibleItemIndex==0&&scrollState.firstVisibleItemScrollOffset>50f){
//            targetValue=1f
//        }
//    }
//    if (!scrollState.isScrollingUp()){
//        if (scrollState.firstVisibleItemIndex==0&&scrollState.firstVisibleItemScrollOffset>50f){
//            targetValue=1f
//        }
//    }
    val targetValue =
        when {
            scrollState.firstVisibleItemIndex != 0 -> 1f
            else -> if (scrollState.firstVisibleItemScrollOffset > targetOffset) 1f else 0f
        }

    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(1000, easing = LinearEasing), label = ""
    )
}
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun PreviewSection(movieDetailsState: UiState<MovieDetails>) {

    when (movieDetailsState) {
        is UiState.Failure -> TODO()
        is UiState.Loading -> {
            LoadingScreen(Modifier.height(180.dp))
        }

        is UiState.Success -> {
            val movieDetails = movieDetailsState.data
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp)
                )
                Text(text = movieDetails.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = getMovieGenresParsed(movieDetails.genres, 3),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DetailsColumn(
                        title = "Year",
                        content = getMovieYearParsed(movieDetails.releaseDate)
                    )
                    DetailsColumn(title = "Country", content = movieDetails.productionCountry)
                    DetailsColumn(
                        title = "length",
                        content = movieDetails.runtime.toString() + " min"
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 52.dp),
                    text = movieDetails.overview,
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
    }

}

@Composable
fun DetailsColumn(title: String, content: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = title, style = MaterialTheme.typography.titleSmall)
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold
        )
    }
}


fun getMovieGenresParsed(genres: List<Genre>, genreLimit: Int) =
    genres.take(genreLimit).map { it.name }.reduceRightOrNull { l, r -> "$l / $r" } ?: ""


fun getMovieYearParsed(date: String) =
    if (date.length >= 4) date.subSequence(0, 4).toString() else ""


