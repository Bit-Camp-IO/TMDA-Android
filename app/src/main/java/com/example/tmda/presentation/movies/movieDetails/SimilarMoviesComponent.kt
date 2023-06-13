package com.example.tmda.presentation.movies.movieDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.domain.enities.movie.Movie
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.shared.ImageCard
import com.example.tmda.presentation.shared.LoadingScreen
import com.example.tmda.presentation.shared.UiState
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.ui.theme.GoldenYellow
import com.example.tmda.ui.theme.PineGreen
import com.example.tmda.ui.theme.WhiteTransparent60
import kotlin.math.roundToInt

@Composable
fun SimilarMoviesRow(
    title: String = "More like this",
    moviesState: UiState<List<Movie>>,
    onCardItemClicked: (Int) -> Unit,
    onSeeAllClicked: () -> Unit
) {
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
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
        TextButton(onClick = onSeeAllClicked, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = "See All",
                color = PineGreen,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }
    when(moviesState){
        is UiState.Failure -> TODO()
        is UiState.Loading -> {LoadingScreen()}
        is UiState.Success -> {
            LazyRow {
                items(count = moviesState.data.size) {
                    SimilarMovieCard(moviesState.data[it], onCardItemClicked)
                }
            }
        }
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp)
    )
}

@Composable
fun SimilarMovieCard(movie: Movie, onCardItemClicked: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(180.dp)
            .clip(mainShape())
            .clickable { onCardItemClicked(movie.id) },
        contentAlignment = Alignment.BottomCenter
    ) {
        ImageCard(
            imagePath = getTmdbImageLink(movie.backdropPath ?: movie.posterPath),
            title = movie.title,
            width = 140.dp,
            height = 180.dp
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
                Text(text = movie.voteAverage.roundToInt().toString())
            }
            Text(text = movie.title, fontSize = 10.sp, maxLines = 1)
            Text(
                text = movie.popularity.toString(),
                fontSize = 8.sp,
                color = WhiteTransparent60,
                maxLines = 1
            )

        }
    }

}