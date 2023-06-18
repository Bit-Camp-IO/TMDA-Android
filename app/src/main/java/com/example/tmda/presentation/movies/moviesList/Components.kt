package com.example.tmda.presentation.movies.moviesList

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.tmda.presentation.movies.uiModels.MovieUiDto
import com.example.tmda.presentation.navigation.navigateToMovieDetails
import com.example.tmda.presentation.shared.base.list.BaseLazyColumn
import com.example.tmda.presentation.shared.base.list.BaseListItemWithBookmark

@Composable
fun MovieList(
    navController: NavController,
    listState: LazyListState = rememberLazyListState(),
    movies: LazyPagingItems<MovieUiDto>,
    addOrRemoveMovieToSavedList: suspend (Int, MutableState<Boolean>) -> Boolean

) {
    BaseLazyColumn(
        lazyItems = movies,
        listState = listState,
        keyGetter = { it.id },
        contentType = { MovieUiDto::class }
    ) {
        MovieListTile(
            movie = movies[it]!!,
            onCardClicked = navController::navigateToMovieDetails,
            onSaveItemClicked = addOrRemoveMovieToSavedList
        )
    }
}

@Composable
fun MovieListTile(
    movie: MovieUiDto,
    onCardClicked: (Int) -> Unit,
    onSaveItemClicked: suspend (Int, MutableState<Boolean>) -> Boolean
) {
    BaseListItemWithBookmark(
        id = movie.id,
        title = movie.title,
        backdropPath = movie.backdropPath,
        date = movie.releaseDate,
        genres = movie.genres,
        language = movie.originalLanguage,
        voteAverage = movie.voteAverage,
        voteCount = movie.voteCount,
        savedState = movie.isSaved,
        onCardClicked = onCardClicked,
        onSaveItemClicked = onSaveItemClicked
    )
}
