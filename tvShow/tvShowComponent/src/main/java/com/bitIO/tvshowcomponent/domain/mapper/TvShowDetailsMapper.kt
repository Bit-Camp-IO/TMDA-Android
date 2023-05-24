package com.bitIO.tvshowcomponent.domain.mapper

import com.bitIO.tvshowcomponent.data.remote.response.TvShowDetailsResponse
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails

internal fun TvShowDetailsResponse.toDomain(): TvShowDetails {
    return TvShowDetails(
        id = id ?: 0,
        title = name ?: "",
        imageURL = "https://image.tmdb.org/t/p/w500$posterPath",
        voteCount = voteCount ?: 0,
        overview = overview ?: ""
    )
}