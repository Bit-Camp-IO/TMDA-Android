package com.bitIO.tvshowcomponent.domain.mapper

import com.bitIO.tvshowcomponent.data.remote.response.TvShowDto
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.entity.TvShowGenre


internal fun TvShowDto.toDomain(): TvShow {
    return TvShow(
        id = this.id!!,
        adult = this.adult ?: false,
        backdropPath = if (this.backdropPath != null) { "https://image.tmdb.org/t/p/w500" + this.backdropPath } else null,
        releaseDate = this.firstAirDate ?: "",
        genres = this.genreIds?.map { it?.toGenre() },
        name = this.name ?: "",
        originalLanguage = this.originalLanguage ?: "",
        overview = this.overview ?: "",
        popularity = this.popularity ?: 0.0,
        posterPath = if (this.posterPath != null) { "https://image.tmdb.org/t/p/w500" + this.posterPath } else null,
        voteAverage = this.voteAverage ?: 0.0,
        voteCount = this.voteCount ?: 0)
}

internal fun Int?.toGenre() = this?.let { TvShowGenre(it, genres[it]) }

internal val genres = mapOf(
    10759 to "Action & Adventure",
    16 to "Animation",
    35 to "Comedy",
    80 to "Crime",
    99 to "Documentary",
    18 to "Drama",
    10751 to "Family",
    10762 to "Kids",
    9648 to "Mystery",
    10763 to "News",
    10764 to "Reality",
    10765 to "Sci-Fi & Fantasy",
    10766 to "Soap",
    10767 to "Talk",
    10768 to "War & Politics",
    37 to "Western",
)
