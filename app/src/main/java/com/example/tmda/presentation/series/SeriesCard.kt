package com.example.tmda.presentation.series

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.entity.TvShowGenre
import com.example.tmda.R
import com.example.tmda.presentation.movies.moviesCardShape
import com.example.tmda.presentation.shared.mainShape
import com.example.tmda.ui.theme.BlackTransparent28
import com.example.tmda.ui.theme.PineGreenDark

val seriesCardShape = mainShape(cornerRadiusDegree = 100f, slopeLength = 30f)

@Composable
fun SeriesCard(show: TvShow, onBookmarkClick: (TvShow) -> Unit) {
    val genres = StringBuilder()
    show.genres?.forEach {
        it?.let {
            if (it != show.genres?.last()) {
                genres.append(it.name).append("/")
            } else {
                genres.append(it.name).append(" . ")
            }
        }
    }
    Surface(
        shape = seriesCardShape, color = BlackTransparent28,
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
                //placeholder = painterResource(id = R.drawable.series_image),
                model = show.posterPath ?: show.backdropPath,
                contentDescription = show.name + " image",
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
                    text = show.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = show.releaseDate.take(4) + " . "
                            + genres.toString()
                            + show.originalLanguage.replaceFirstChar { it.uppercase() },
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
                        text = show.popularity.toInt().toString() + "%",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )

                }
            }
            Icon(
                painterResource(id = R.drawable.ic_bookmark),
                contentDescription = null,
                tint = PineGreenDark,
                modifier = Modifier.size(30.dp)
            )
        }


    }
}

@Preview
@Composable
fun PreviewTvSeriesCard() {
    val tv = TvShow(
        1,
        true,
        "",
        "2012",
        listOf(
            TvShowGenre(1, "Horror"),
            TvShowGenre(2, "Drama")
        ),
        "The Last Of Us",
        "en",
        "bla bla bla",
        12.0,
        "",
        8.9,
        11752
    )

    SeriesCard(show = tv, {})
}

