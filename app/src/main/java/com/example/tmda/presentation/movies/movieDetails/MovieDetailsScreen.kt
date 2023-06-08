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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movies.domain.enities.Genre
import com.example.tmda.presentation.movies.CreditsComponent
import com.example.tmda.presentation.shared.BackGround
import com.example.tmda.presentation.shared.MotionLayoutAppBar
import com.example.tmda.presentation.shared.UiState


@Composable

fun MovieDetailsScreen(navController: NavController) {
    val viewModel = hiltViewModel<MoviesDetailsViewModel>()
    when (val uiState = viewModel.movieScreenDetails.value) {
        is UiState.Failure -> BackGround()
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success<MovieDetailsScreenDto> -> DetailsScreenLoaded(
            uiState.data,
            viewModel.isSaved.value,
            viewModel::addOrRemoveMovieToSavedList
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreenLoaded(
    movieDetails: MovieDetailsScreenDto,
    isSaved: Boolean,
    onSavedClicked: () -> Unit
) {
    val scrollState = rememberLazyListState()
    val progress = calculateProgress(scrollState)
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent), state = scrollState
        ) {
            stickyHeader {
                MotionLayoutAppBar(
                    progress = progress.value,
                    movieDetails = movieDetails,
                    isSaved = isSaved,
                    onSavedClicked = onSavedClicked
                )
            }
            item { PreviewSection(movieDetails = movieDetails) }
            item {
                CreditsComponent(
                    title = "Cast",
                    creditItems = movieDetails.credits.cast,
                    onSeeAllClicked = {},
                    onCardClicked = {})
            }
            item {
                CreditsComponent(
                    title = "crew",
                    creditItems = movieDetails.credits.crew,
                    onSeeAllClicked = {},
                    onCardClicked = {})
            }
            item { SimilarMoviesRow("Similar Movies", movieDetails.similarMovies) {} }
            item { SimilarMoviesRow("Related", movieDetails.recommendedMovies) {} }
            //  item { SimilarMoviesRow {} }
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
        animationSpec = tween(1000, easing = LinearEasing), label = ""
    )
}


@Composable
fun PreviewSection(movieDetails: MovieDetailsScreenDto) {
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            DetailsColumn(title = "Year", content = getMovieYearParsed(movieDetails.releaseDate))
            DetailsColumn(title = "Country", content = movieDetails.productionCountry)
            DetailsColumn(title = "length", content = movieDetails.runtime.toString() + " min")
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
    genres.take(genreLimit).map { it.name }.reduce { l, r -> "$l / $r" }


fun getMovieYearParsed(date: String) =
    date.subSequence(0, 4).toString()


