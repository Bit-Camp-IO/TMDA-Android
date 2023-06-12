package com.example.tmda.presentation.movies.moviesHome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.movies.domain.enities.Movie
import com.example.tmda.R
import com.example.tmda.presentation.shared.ImageCard
import com.example.tmda.presentation.shared.ItemsLazyRowComponent
import com.example.tmda.ui.theme.PineGreen
import com.example.tmda.ui.theme.WhiteTransparent60
import kotlin.math.ceil
import kotlin.math.floor

@Preview
@Composable
fun MoviesHomeScreen() {
    LazyColumn {
        item { NowPlayingHeader() }
        item {
            ItemsLazyRowComponent<Movie>(
                title = "New Movies",
                onSeeAllClicked = { /*TODO*/ },
                items = listOf()
            ) { MovieHomeCard(movie = it) }
        }
        item {
            ItemsLazyRowComponent<Movie>(
                title = "Upcoming",
                onSeeAllClicked = { /*TODO*/ },
                items = listOf()
            ) { MovieHomeCard(movie = it) }
        }
        item {
            ItemsLazyRowComponent<Movie>(
                title = "Top Rated",
                hasBottomDivider = false,
                onSeeAllClicked = { /*TODO*/ },
                items = listOf()
            ) { MovieHomeCard(movie = it) }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NowPlayingHeader() {
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) { 5 }
    Column(Modifier.background(Color.Transparent)) {
        HorizontalPager(state = pagerState) { NowPlayingCard() }
        Spacer(Modifier.height(8.dp))
        DotsIndicator(totalDots = pagerState.pageCount, currentIndex = pagerState.currentPage)

    }

}

@Composable
fun NowPlayingCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.doctor_strange),
            contentScale = ContentScale.FillBounds,
            contentDescription = "movie poster"
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "Marvel Studios",
                style = MaterialTheme.typography.bodySmall,
                color = PineGreen
            )
            Text(text = "Doctor Strange", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "In The Multiverse Of  Madness",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            TotalRatingIcons(rating = 7f, iconSize = 24.dp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "From 324 users",
                style = MaterialTheme.typography.bodySmall,
                color = WhiteTransparent60
            )

        }
    }
}

@Composable
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

@Composable
fun DotsIndicator(totalDots: Int, currentIndex: Int) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        for (i in 0 until totalDots) {
            val currentShape =
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
fun MovieHomeCard(movie: Movie?) {
    Box(contentAlignment = Alignment.BottomCenter) {
        ImageCard(movie?.posterPath)
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = movie?.title ?: "No Time To Die",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = movie?.title ?: "2022 ‧ Sci-fi/Action",
                style = MaterialTheme.typography.labelSmall
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = (movie?.voteAverage ?: 9.8).toString(),
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                TotalRatingIcons(rating = (movie?.voteAverage ?: 9.8).toFloat(), iconSize = 16.dp)
            }
        }

    }

}

fun getMoviesYearAndGenres(movie: Movie): String {
    val genresName = movie.genres.take(2).map { it.name }.reduce { l, r -> "$l/ $r" }
    return movie.releaseDate.subSequence(0, 4).toString() + "." + genresName

}