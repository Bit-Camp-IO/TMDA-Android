package com.example.tmda.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.authentication.domain.entities.UserDetails
import com.example.movies.domain.enities.movie.Movie
import com.example.tmda.presentation.movies.getTmdbImageLink
import com.example.tmda.presentation.movies.movieDetails.SimilarMoviesRow
import com.example.tmda.presentation.series.seriesDetails.SimilarSeriesRow
import com.example.tmda.presentation.shared.uiStates.UiState

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val user = (viewModel.userDetails.value as UiState.Success).data
    val movies = viewModel.userMovies.value

    Column {
        UserInfoRow(userDetails = user)
        BookMarkedMovies(movieState = movies, onCardItemClicked = {})
    }

}

@Composable
fun UserInfoRow(userDetails: UserDetails) {
    Row {
        Row {
            AsyncImage(model = getTmdbImageLink(userDetails.userImage), contentDescription = null)
            Column {
                Text(text = userDetails.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text =userDetails.username, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
fun BookMarkedMovies(movieState: UiState<List<Movie>>, onCardItemClicked: (Int) -> Unit) {
    SimilarMoviesRow(moviesState = movieState, onCardItemClicked = onCardItemClicked) {

    }
}

@Composable
fun BookMarkedSeries(seriesState: UiState<List<TvShow>>, onCardItemClicked: (Int) -> Unit) {
    SimilarSeriesRow(seriesState = seriesState, onCardItemClicked = onCardItemClicked) {

    }
}