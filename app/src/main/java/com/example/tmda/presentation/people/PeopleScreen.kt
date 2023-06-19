package com.example.tmda.presentation.people


import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.movies.movieDetails.SimilarMoviesRow
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.presentation.series.seriesDetails.SimilarSeriesRow
import com.example.tmda.presentation.shared.AppToolBar
import com.example.tmda.presentation.shared.uiStates.ErrorScreen
import com.example.tmda.presentation.shared.uiStates.LoadingScreen
import com.example.tmda.presentation.shared.uiStates.UiState
import com.example.tmda.ui.theme.PineGreenLight

@Composable
fun PersonSearchScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<PersonSearchViewModel>()
    val personDetailsState = viewModel.personDetails
    val movies = viewModel.personMovies
    val series = viewModel.personSeries
    Box {
        when (val personDetails = personDetailsState.value) {
            is UiState.Failure -> ErrorScreen {}
            is UiState.Loading -> LoadingScreen()
            is UiState.Success -> {
                val person = personDetails.data
                LazyColumn {
                    item { PersonImage(image = person.profilePath, name = person.name) }
                    item {
                        PersonDetails(
                            birthdate = person.birthday,
                            moviesCount = when (val movies = movies.value) {
                                is UiState.Success -> {
                                    movies.data.size.toString()
                                }

                                else -> "N/A"
                            },
                            overView = person.biography
                        )
                    }
                    item {
                        SimilarMoviesRow(
                            title = "Movies",
                            moviesState = movies.value,
                            hasSeeAll = false,
                            onCardItemClicked = navController::navigateToMovieDetails
                        ) {}
                    }
                    item {
                        SimilarSeriesRow(
                            title = "Series",
                            seriesState = series.value,
                            hasSeeAll = false,
                            onCardItemClicked = navController::navigateToTvShowDetailsScreen
                        ) {

                        }
                    }
                }
            }
        }
        AppToolBar(navController = navController) {}

    }

}

@Composable
fun PersonImage(image: String?, name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomEnd = 64.dp)),
            model = getTmdbImageLink(image), contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp, start = 24.dp)
        )
    }

}

@Composable
fun PersonDetails(birthdate: String, moviesCount: String, overView: String) {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Row(verticalAlignment = Alignment.Bottom) {
            Icon(painter = painterResource(id = R.drawable.ic_cake), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = birthdate, style = MaterialTheme.typography.titleSmall)

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.ic_movie), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = moviesCount, style = MaterialTheme.typography.titleSmall)

        }
        Spacer(modifier = Modifier.height(16.dp))

        ExpandedCard(overView = overView)
    }

}

@Composable
fun ExpandedCard(overView: String) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Divider(Modifier.size(5.dp, 20.dp), color = PineGreenLight)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Overview")
            }
            Text(
                text = if (isExpanded.value) "Show Less" else "Show More",
                Modifier.clickable { isExpanded.value = !isExpanded.value })
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = overView,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            maxLines = if (isExpanded.value) Int.MAX_VALUE else 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}
