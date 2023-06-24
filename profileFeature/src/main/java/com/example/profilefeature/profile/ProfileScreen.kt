package com.example.profilefeature.profile

import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.moviesComponent.domain.enities.movie.Movie
import com.example.moviesfeature.uiModels.MoviesScreenType
import com.example.profilefeature.navigation.ProfileNavigator
import com.example.sharedui.R
import com.example.sharedui.uiStates.UiState
import com.example.sharedui.utils.getTmdbImageLink
import com.example.tvshowfeature.seriesList.SeriesScreenType
import com.example.user.domain.entities.UserDetails

@Composable
fun ProfileScreen(
    profileNavigator: ProfileNavigator,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val user = viewModel.userDetails.value
    val movies = viewModel.userMovies.value
    val series = viewModel.userSeries.value
    val allData =
        viewModel.userDetails.value + viewModel.userMovies.value + viewModel.userSeries.value
    when (allData.any { it is UiState.Failure }) {
        true -> com.example.sharedui.uiStates.ErrorScreen(viewModel::updateAll)
        false -> Column {
            Spacer(modifier = Modifier.height(72.dp))
            UserInfoRow(userDetailsState = user, viewModel::logOut)
            BookMarkedMovies(
                movieState = movies,
                onCardItemClicked = profileNavigator::navigateToMovieDetails,
                onSeeAllClicked = {
                    profileNavigator.navigateToMovieListScreen(
                        "Bookmarked",
                        MoviesScreenType.Bookmarked
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            BookMarkedSeries(
                seriesState = series,
                onCardItemClicked = profileNavigator::navigateToTvShowDetailsScreen,

                ) { profileNavigator.navigateToShowsListScreen(SeriesScreenType.Bookmarked) }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.updateAll()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    onBackPressedDispatcher?.addCallback(owner = lifecycleOwner) {
        profileNavigator.navController .navigate(profileNavigator.baseRoute) {
            popUpTo(profileNavigator.navController .graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

}


@Composable
fun UserInfoRow(userDetailsState: UiState<UserDetails>, onLogOut: () -> Unit) {

    when (userDetailsState) {
        is UiState.Failure -> com.example.sharedui.uiStates.ErrorComponent {}
        is UiState.Loading -> com.example.sharedui.uiStates.LoadingScreen(
            Modifier.height(100.dp)
        )
        is UiState.Success -> {
            val userDetails = userDetailsState.data
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
                        Text(
                            text = userDetails.name,
                            style = MaterialTheme.typography.titleMedium
                        )
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
    movieState:UiState<List<Movie>>,
    onCardItemClicked: (Int) -> Unit,
    onSeeAllClicked: () -> Unit
) {
    com.example.moviesfeature.movieDetails.SimilarMoviesRow(
        title = "your Bookmarked Movies",
        moviesState = movieState,
        onCardItemClicked = onCardItemClicked,
        onSeeAllClicked = onSeeAllClicked
    )
}

@Composable
fun BookMarkedSeries(
    seriesState: UiState<List<TvShow>>,
    onCardItemClicked: (Int) -> Unit,
    onSeeAllClicked: () -> Unit
) {
    com.example.tvshowfeature.seriesDetails.SimilarSeriesRow(
        title = "your Bookmarked Series",
        seriesState = seriesState,
        onCardItemClicked = onCardItemClicked,
        onSeeAllClicked = onSeeAllClicked
    )
}