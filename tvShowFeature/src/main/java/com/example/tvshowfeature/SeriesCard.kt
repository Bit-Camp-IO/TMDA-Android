package com.example.tvshowfeature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.sharedui.base.list.BaseListItemWithBookmark
import com.example.sharedui.x.mainShape
import com.example.tvshowfeature.uiDto.TvShowBookMarkUiModel

val seriesCardShape = mainShape(cornerRadiusDegree = 100f, slopeLength = 30f)

@Composable
fun SeriesCard(
    tvShowBookMark: TvShowBookMarkUiModel,
    onCardClick: (Int) -> Unit,
    onBookmarkClick: suspend (Int,MutableState<Boolean>) -> Boolean
) {
    BaseListItemWithBookmark(
        id = tvShowBookMark.tvShowUiModel.id,
        title = tvShowBookMark.tvShowUiModel.title,
        backdropPath = tvShowBookMark.tvShowUiModel.backdropPath,
        date = tvShowBookMark.tvShowUiModel.releaseDate,
        genres = tvShowBookMark.tvShowUiModel.genres,
        language = tvShowBookMark.tvShowUiModel.originalLanguage,
        voteAverage = tvShowBookMark.tvShowUiModel.voteAverage,
        voteCount = tvShowBookMark.tvShowUiModel.voteCount,
        savedState = tvShowBookMark.bookMarkState,
        onCardClicked = onCardClick,
        onSaveItemClicked = onBookmarkClick
    )
}
