package com.example.tmda.presentation.movies.moviesHome.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movies.domain.enities.movie.Movie
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.shared.ErrorScreen
import com.example.tmda.presentation.shared.LoadingScreen
import com.example.tmda.presentation.shared.UiState
import com.example.tmda.ui.theme.PineGreenDark
import com.example.tmda.ui.theme.WhiteTransparent60

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NowPlayingHeader(moviesUiState: UiState<List<Movie>>) {

    when (moviesUiState) {
        is UiState.Failure -> {
            ErrorScreen {}
        }

        is UiState.Loading -> {
            LoadingScreen(Modifier.height(416.dp))
        }

        is UiState.Success -> {
            val moviesList = moviesUiState.data
            val pagerState =
                rememberPagerState(
                    initialPage = 0,
                    initialPageOffsetFraction = 0f
                ) { moviesList.size }
            Column {
                HorizontalPager(state = pagerState) {
                    NowPlayingCard(moviesList[it])
                }
                Spacer(Modifier.height(8.dp))
                DotsIndicator(
                    totalDots = pagerState.pageCount,
                    currentIndex = pagerState.currentPage
                )

            }
        }
    }


}
@Composable
fun NowPlayingCard(movie: Movie) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = getTmdbImageLink(movie.posterPath!!),
            contentDescription = movie.title + "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "Marvel Studios",
                style = MaterialTheme.typography.bodySmall,
                color = PineGreenDark
            )
            Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            TotalRatingIcons(rating = movie.voteAverage.toFloat(), iconSize = 24.dp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "From ${movie.voteCount} users",
                style = MaterialTheme.typography.bodySmall,
                color = WhiteTransparent60
            )

        }
    }
}
@Composable
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

