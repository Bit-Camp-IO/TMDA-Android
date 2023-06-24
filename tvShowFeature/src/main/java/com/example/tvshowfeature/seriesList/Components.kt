package com.example.tvshowfeature.seriesList

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.paging.compose.LazyPagingItems
import com.example.tvshowfeature.navigation.TvShowNavigator
import com.example.tvshowfeature.uiDto.TvShowBookMarkUiModel

@Composable
fun SeriesList(
    tvShowNavigator: TvShowNavigator,
    listState: LazyListState = rememberLazyListState(),
    series: LazyPagingItems<TvShowBookMarkUiModel>,
    addOrRemoveSeriesToSavedList: suspend (Int, MutableState<Boolean>) -> Boolean
) {
    com.example.sharedui.base.list.BaseLazyColumn(
        lazyItems = series,
        listState = listState,
        keyGetter = { it.tvShowUiModel.id },
        contentType = { TvShowBookMarkUiModel::class }
    ) {
        SeriesListTile(
            series = series[it]!!,
            onCardClicked = tvShowNavigator::navigateToTvShowDetailsScreen,
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
    com.example.sharedui.base.list.BaseListItemWithBookmark(
        id = series.tvShowUiModel.id,
        title = series.tvShowUiModel.title,
        backdropPath = series.tvShowUiModel.backdropPath ?: series.tvShowUiModel.posterPath,
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
