package com.example.tmda.presentation.movies.moviesHome.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import com.example.tmda.presentation.shared.ImageCard
import com.example.tmda.presentation.shared.imageCardModifier
import com.example.tmda.presentation.shared.mainShape

@Composable
fun MovieHomeCard(movie: MovieUiDto, onClick: (Int) -> Unit) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = HomeCardModifier
            .clickable { onClick(movie.id) }
    ) {
        HomeCardImage(
            title = movie.title,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(170.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(
                text = movie.releaseDate.take(4) + "." + movie.genres,
                style = MaterialTheme.typography.labelSmall
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.voteAverage.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                TotalRatingIcons(rating = movie.voteAverage.toFloat(), iconSize = 16.dp)
            }
        }

    }

}

@Composable
fun HomeCardImage(title: String, posterPath: String?, backdropPath: String?) {
    ImageCard(
        imagePath = posterPath ?: backdropPath,
        title = title,
        modifier = imageCardModifier
    )
}

val HomeCardModifier= Modifier
    .size(200.dp, 270.dp)
    .clip(mainShape)
val imageCardModifier = Modifier.imageCardModifier(200.dp, 270.dp)