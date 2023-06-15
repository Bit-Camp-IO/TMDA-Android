package com.example.tmda.presentation.series

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.movies.moviesCardShape
import com.example.tmda.presentation.series.uiDto.TvShowBookMarkUiModel
import com.example.tmda.presentation.shared.SavedItemIcon
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.presentation.shared.toSuccessState
import com.example.tmda.ui.theme.BlackTransparent28
import kotlinx.coroutines.launch

val seriesCardShape = mainShape(cornerRadiusDegree = 100f, slopeLength = 30f)

@Composable
fun SeriesCard(
    tvShowBookMark: TvShowBookMarkUiModel,
    onCardClick: (Int) -> Unit,
    onBookmarkClick: suspend (Int, Boolean) -> Boolean
) {
    val show = tvShowBookMark.tvShowUiModel
    Surface(
        shape = seriesCardShape, color = BlackTransparent28,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 8.dp).clickable {
                onCardClick(show.id)
            }

    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            AsyncImage(
                model = getTmdbImageLink(show.posterPath ?: show.backdropPath),
                contentDescription = show.title + " image",
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
                    text = show.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = show.releaseDate + " . " + show.genres + show.originalLanguage.replaceFirstChar { it.uppercase() },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
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
                            text = show.voteAverage.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    }
                    Text(
                        text = show.voteCount.toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )

                }
            }
            val coroutineScope = rememberCoroutineScope()
            var isSavedState by remember { tvShowBookMark.bookMarkState }
            SavedItemIcon(modifier = Modifier.clickable {
                coroutineScope.launch {
                    if (onBookmarkClick(
                            tvShowBookMark.tvShowUiModel.id,
                            tvShowBookMark.bookMarkState.value
                        )
                    ) {
                        tvShowBookMark.bookMarkState.value = !tvShowBookMark.bookMarkState.value
                        isSavedState = tvShowBookMark.bookMarkState.value
                    }
                }
            }, isSavedState = isSavedState.toSuccessState())

        }


    }
}
