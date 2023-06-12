package com.example.tmda.presentation.movies.movieDetails

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tmda.R
import com.example.tmda.presentation.shared.BackGround
import com.example.tmda.presentation.shared.MotionLayoutAppBar
import com.example.tmda.ui.theme.BlackTransparent60
import com.example.tmda.ui.theme.GoldenYellow
import com.example.tmda.ui.theme.PineGreen
import com.example.tmda.ui.theme.WhiteTransparent60

const val dummyText =
    "A twisted tale of two estranged sisters whose reunion is cut short by the rise of flesh-possessing demons, thrusting them into a primal battle for survival as they face the most nightmarish version of family imaginable."

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun MovieDetailsScreen() {
    val scrollState = rememberLazyListState()
    val progress = calculateProgress(scrollState)
    Box {
        BackGround()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent), state = scrollState
        ) {
            stickyHeader { MotionLayoutAppBar(progress = progress.value, imageUrl = "", voteAvg = 0.0, voteCount = 0) }
            item { PreviewSection() }
            item { CastsRow {} }
            item { SimilarMoviesRow {} }
            item { SimilarMoviesRow {} }
            item { SimilarMoviesRow {} }
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
        animationSpec = tween(2000, easing = LinearOutSlowInEasing), label = ""
    )
}


@Composable
fun PreviewSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp)
        )
        Text(text = "Evil Dead Rise", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Horror/Fantasy/Slasher", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            DetailsColumn(title = "Year", content = "2023")
            DetailsColumn(title = "Country", content = "USA")
            DetailsColumn(title = "length", content = "96 min")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(horizontal = 52.dp),
            text = dummyText,
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
        Text(text = content, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun CastsRow(title: String = "Casts", onSeeAllClicked: () -> Unit) {

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
        TextButton(onClick = { /*TODO*/ }, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = "See All",
                color = PineGreen,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }

    LazyRow {
        items(count = 20) {
            ActorCard()
        }

    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp)
    )
}

@Composable
fun ActorCard(actorName: String = "Mirabai Pease", characterName: String = "Teresa") {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(100.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.series_image),
            contentScale = ContentScale.FillBounds,
            contentDescription = "actor image"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(color = BlackTransparent60)
                .padding(8.dp)
        ) {
            Text(text = actorName, fontSize = 10.sp)
            Text(text = actorName, fontSize = 10.sp, color = WhiteTransparent60)

        }
    }

}

@Composable
fun SimilarMoviesRow(title: String = "More like this", onSeeAllClicked: () -> Unit) {

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
        TextButton(onClick = { /*TODO*/ }, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = "See All",
                color = PineGreen,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }

    LazyRow {
        items(count = 20) {
            SimilarMovieCard()
        }
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp)
    )
}

@Composable
fun SimilarMovieCard(movieName: String = "Evil Dead", details: String = "2013 R 1h 31m") {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(100.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.movie_image),
            contentScale = ContentScale.FillBounds,
            contentDescription = "actor image"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(color = BlackTransparent60)
                .padding(8.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    tint = GoldenYellow,
                    contentDescription = null
                )
                Text(text = "6.8")
            }
            Text(text = movieName, fontSize = 10.sp)
            Text(text = movieName, fontSize = 8.sp, color = WhiteTransparent60)

        }
    }

}