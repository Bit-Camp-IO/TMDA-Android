package com.bitIO.tvshowcomponent.domain.mapper

import com.bitIO.tvshowcomponent.data.remote.response.TvShowDto
import com.bitIO.tvshowcomponent.domain.entity.TvShow


internal fun TvShowDto.toDomain(): TvShow {
    return TvShow(
        id = id ?: 0,
        title = name ?: "",
        imageURL = "https://image.tmdb.org/t/p/w500$posterPath"
    )
}

internal fun List<TvShowDto>.toDomain(): List<TvShow> = map { it.toDomain() }