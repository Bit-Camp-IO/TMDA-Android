package com.example.tmda.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.authentication.domain.entities.UserDetails
import com.example.movies.domain.enities.movie.Movie
import com.example.tmda.R
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.movies.movieDetails.SimilarMoviesRow
import com.example.tmda.presentation.movies.uiModels.MoviesScreenType
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.navigation.navigateToMovieListScreen
import com.example.tmda.presentation.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.presentation.series.seriesDetails.SimilarSeriesRow
import com.example.tmda.presentation.shared.uiStates.ErrorComponent
import com.example.tmda.presentation.shared.uiStates.LoadingScreen
import com.example.tmda.presentation.shared.uiStates.UiState

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val user = viewModel.userDetails.value
    val movies = viewModel.userMovies.value
    val series = viewModel.userSeries.value

    Column {
        Spacer(modifier = Modifier.height(72.dp))
        UserInfoRow(userDetails = user, viewModel::logOut)
        BookMarkedMovies(
            movieState = movies,
            onCardItemClicked = navController::navigateToMovieDetails,
            onSeeAllClicked = {
                navController.navigateToMovieListScreen(
                    "Bookmarked",
                    MoviesScreenType.Bookmarked
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        BookMarkedSeries(
            seriesState = series,
            onCardItemClicked = navController::navigateToTvShowDetailsScreen
        )
    }

}

@Composable
fun UserInfoRow(userDetails: UiState<UserDetails>, onLogOut: () -> Unit) {

    when (val userDetails = userDetails) {
        is UiState.Failure -> ErrorComponent {}
        is UiState.Loading -> LoadingScreen(Modifier.height(100.dp))
        is UiState.Success -> {
            val userDetails = userDetails.data
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(24.dp))
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(105.dp),
                            painter = painterResource(id = R.drawable.image_border),
                            contentDescription = null
                        )
                        AsyncImage(
                            model = getTmdbImageLink(userDetails.userImage),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = userDetails.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = userDetails.username,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                IconButton(onClick = onLogOut) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = null
                    )
                }
            }
        }
    }

}

@Composable
fun BookMarkedMovies(
    movieState: UiState<List<Movie>>,
    onCardItemClicked: (Int) -> Unit,
    onSeeAllClicked: () -> Unit
) {
    SimilarMoviesRow(
        title = "your Bookmarked Movies",
        moviesState = movieState,
        onCardItemClicked = onCardItemClicked,
        onSeeAllClicked = onSeeAllClicked
    )
}

@Composable
fun BookMarkedSeries(seriesState: UiState<List<TvShow>>, onCardItemClicked: (Int) -> Unit) {
    SimilarSeriesRow(
        title = "your Bookmarked Series",
        seriesState = seriesState, onCardItemClicked = onCardItemClicked,
    ) {

    }
}