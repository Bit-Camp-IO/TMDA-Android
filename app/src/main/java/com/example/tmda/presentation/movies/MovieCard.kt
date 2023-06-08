package com.example.tmda.presentation.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movies.domain.enities.movie.Movie
import com.example.tmda.R
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.ui.theme.BlackTransparent28


@Composable
fun MovieCard(movie: Movie) {
    Surface(
        shape = moviesCardShape, color = BlackTransparent28,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 8.dp)

    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {

            AsyncImage(
                model = getTmdbImageLink(movie.backdropPath ?: movie.posterPath!!),
                contentDescription = movie.title + "image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(144.dp)
                    .clip(moviesCardShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
            ) {
                Text(
                    text = movie.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    text = movie.releaseDate.take(4) + ". genre/genre ." + movie.originalLanguage,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = movie.voteAverage.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }
                    Text(
                        text = movie.popularity.toString() + "%",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )

                }
            }
            // BookMarkIconButton { TODO("not impl") }
        }


    }
}

val moviesCardShape = mainShape(cornerRadiusDegree = 100f, slopeLength = 30f)