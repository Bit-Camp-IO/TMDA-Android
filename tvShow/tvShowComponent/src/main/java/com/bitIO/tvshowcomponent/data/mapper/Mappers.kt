package com.bitIO.tvshowcomponent.data.mapper

import com.bitIO.tvshowcomponent.data.remote.dto.tvShow.TvShowDto
import com.bitIO.tvshowcomponent.data.remote.dto.tvShow.TvShowDtoPage
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.entity.TvShowPage

fun TvShowDto.toTVShow() =
    TvShow(
        id = id,
        adult = adult?:false,
        backdropPath = backdropPath,
        releaseDate = firstAirDate,
        genres = genreIds.map { it.toGenre() },
        name = name,
        originalLanguage = originalLanguage,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
fun TvShowDtoPage.toTvShowPage(): TvShowPage {
    return TvShowPage(
        page=page,
        results = results.map { it.toTVShow() },
        totalPages=totalPages,
        totalResults=totalResults
    )
}
