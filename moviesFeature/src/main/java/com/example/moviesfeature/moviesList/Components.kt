package com.example.moviesfeature.moviesList

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.paging.compose.LazyPagingItems
import com.example.moviesfeature.navigation.MoviesNavigator
import com.example.moviesfeature.uiModels.MovieUiDto
import com.example.sharedui.base.list.BaseLazyColumn
import com.example.sharedui.base.list.BaseListItemWithBookmark

@Composable
fun MovieList(
    moviesNavigator: MoviesNavigator,
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
            onCardClicked = moviesNavigator::navigateToMovieDetails,
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
