package com.bitIO.tvshowcomponent.domain.mapper

import com.bitIO.tvshowcomponent.data.remote.response.TvShowDetailsResponse
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails
import com.squareup.moshi.Json

internal fun TvShowDetailsResponse.toDomain(): TvShowDetails {
    return TvShowDetails(
        id = id ?: 0,
        title = name ?: "",
        imageURL = "https://image.tmdb.org/t/p/w500/$posterPath",
        voteCount = voteCount ?: 0,
        voteAverage = voteAverage ?: 0.0,
        overview = overview ?: "",
        lastEpisode = lastEpisodeToAir?.episodeNumber ?: 0,
        lastSeason = lastEpisodeToAir?.seasonNumber ?: 0,
        network = networks?.get(0)?.name ?: "",
        genres = genres?.map { it.id?.toGenre()!! }!!,
        date = firstAirDate ?: "",
        originCountry = originCountry?.get(0) ?: "",
    )
}