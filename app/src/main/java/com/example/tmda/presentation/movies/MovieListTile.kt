package com.example.tmda.presentation.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.tmda.presentation.movies.uiModels.MovieUiDto
import com.example.tmda.presentation.shared.base.list.BaseListItemWithBookmark


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
