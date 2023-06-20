package com.example.tmda.presentation.series.seriesList

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.tmda.presentation.navigation.navigateToTvShowDetailsScreen
import com.example.tmda.presentation.series.uiDto.TvShowBookMarkUiModel
import com.example.tmda.presentation.shared.base.list.BaseLazyColumn
import com.example.tmda.presentation.shared.base.list.BaseListItemWithBookmark

@Composable
fun SeriesList(
    navController: NavController,
    listState: LazyListState = rememberLazyListState(),
    series: LazyPagingItems<TvShowBookMarkUiModel>,
    addOrRemoveSeriesToSavedList: suspend (Int, MutableState<Boolean>) -> Boolean
) {
    BaseLazyColumn(
        lazyItems = series,
        listState = listState,
        keyGetter = { it.tvShowUiModel.id },
        contentType = { TvShowBookMarkUiModel::class }
    ) {
        SeriesListTile(
            series = series[it]!!,
            onCardClicked = navController::navigateToTvShowDetailsScreen,
            onSaveItemClicked = addOrRemoveSeriesToSavedList
        )
    }

}

@Composable
fun SeriesListTile(
    series: TvShowBookMarkUiModel,
    onCardClicked: (Int) -> Unit,
    onSaveItemClicked: suspend (Int, MutableState<Boolean>) -> Boolean
) {
    BaseListItemWithBookmark(
        id = series.tvShowUiModel.id,
        title = series.tvShowUiModel.title,
        backdropPath = series.tvShowUiModel.backdropPath?:series.tvShowUiModel.posterPath,
        date = series.tvShowUiModel.releaseDate,
        genres = series.tvShowUiModel.genres,
        language = series.tvShowUiModel.originalLanguage,
        voteAverage = series.tvShowUiModel.voteAverage,
        voteCount = series.tvShowUiModel.voteCount,
        savedState = series.bookMarkState,
        onCardClicked = onCardClicked,
        onSaveItemClicked = onSaveItemClicked
    )
}
