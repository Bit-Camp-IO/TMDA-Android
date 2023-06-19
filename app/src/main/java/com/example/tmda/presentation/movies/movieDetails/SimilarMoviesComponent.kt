package com.example.tmda.presentation.movies.movieDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.example.tmda.presentation.shared.base.BaseImageCard
import com.example.tmda.presentation.shared.base.BaseLazyRowComponent
import com.example.tmda.presentation.shared.base.imageCardModifier
import com.example.tmda.presentation.shared.base.mainShape
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.ui.theme.GoldenYellow
import com.example.tmda.ui.theme.WhiteTransparent60
import kotlin.math.roundToInt
@Composable
fun SimilarMoviesRow(
    title: String = "More like this",
    moviesState: UiState<List<Movie>>,
    hasSeeAll:Boolean = true,
    onCardItemClicked: (Int) -> Unit,
    onSeeAllClicked: () -> Unit
) {
    BaseLazyRowComponent(
        title = title,
        onSeeAllClicked = onSeeAllClicked,
        itemsState = moviesState,
        hasSeeAll=hasSeeAll,
        onItemClicked = onCardItemClicked
    ) { movie,_->
        SimilarMovieCard(movie =movie , onCardItemClicked =onCardItemClicked)
    }
}

val shape = mainShape

@Composable
fun SimilarMovieCard(movie: Movie, onCardItemClicked: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(180.dp)
            .clip(shape)
            .clickable { onCardItemClicked(movie.id) },
        contentAlignment = Alignment.BottomCenter
    ) {
        BaseImageCard(
            imagePath = getTmdbImageLink(movie.backdropPath ?: movie.posterPath),
            title = movie.title,
            modifier = similarMovieCardModifier
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

val similarMovieCardModifier = Modifier.imageCardModifier(140.dp, 180.dp)
