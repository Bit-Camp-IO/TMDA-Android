package com.example.tmda.presentation.series.uiDto

import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.example.tmda.presentation.shared.paging.UiPage

fun TvShow.toTvShowUIModel(): TvShowUiModel {
    return TvShowUiModel(
        backdropPath = this.backdropPath,
        genres = this.genres.take(2).joinToString { it.name },
        id = id,
        originalLanguage = originalLanguage,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = name,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}

fun TvShowPage.toUiPage(): UiPage<TvShowUiModel> {
    return UiPage(
        page = page,
        results = results.map { it.toTvShowUIModel() },
        totalPages = totalPages,
    )
}