package com.example.tvshowfeature.uiDto

import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.entity.TvShowPage

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

fun TvShowPage.toUiPage(): com.example.sharedui.paging.UiPage<TvShowUiModel> {
    return com.example.sharedui.paging.UiPage(
        page = page,
        results = results.map { it.toTvShowUIModel() },
        totalPages = totalPages,
    )
}