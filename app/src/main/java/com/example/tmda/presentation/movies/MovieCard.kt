package com.example.tmda.presentation.movies

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tmda.R
import com.example.tmda.presentation.movies.moviesList.MovieUiDto
import com.example.tmda.presentation.shared.SavedItemIcon
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.presentation.shared.UiStates.toSuccessState
import com.example.tmda.ui.theme.BlackTransparent28
import kotlinx.coroutines.launch


@Composable
fun MovieCard(
    movie: MovieUiDto,
    hasBookMarkIcon: Boolean,
    onCardClicked: (Int) -> Unit,
    onSaveItemClicked: suspend (Int, Boolean) -> Boolean
) {

    var isSavedState by remember { movie.isSaved }
    Surface(
        shape = moviesCardShape, color = BlackTransparent28,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 8.dp)
            .clickable { onCardClicked(movie.id) }

    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            val coroutineScope = rememberCoroutineScope()
            AsyncImage(
                model = getTmdbImageLink(movie.backdropPath ),
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
                    .fillMaxHeight()
                    .padding(end = 16.dp),
            ) {
                if (hasBookMarkIcon) Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    SavedItemIcon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                coroutineScope.launch {
                                  if ( onSaveItemClicked(movie.id, movie.isSaved.value)){
                                    movie.isSaved.value = !movie.isSaved.value
                                    isSavedState = movie.isSaved.value
                                }}
                            },
                        isSavedState = isSavedState.toSuccessState()
                    )

                } else Spacer(modifier = Modifier.height(24.dp))

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = movie.releaseDate.take(4) + ". ${movie.genres} ." + movie.originalLanguage,
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
                        text = movie.voteCount.toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )

                }
            }
        }


    }
}

val moviesCardShape = mainShape(cornerRadiusDegree = 100f, slopeLength = 30f)