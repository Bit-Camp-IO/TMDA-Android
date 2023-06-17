package com.bitIO.tvshowcomponent.data.mapper

import com.bitIO.tvshowcomponent.data.remote.dto.tvShow.details.TvShowDetailsDto
import com.bitIO.tvshowcomponent.domain.entity.TvShowDetails

 fun TvShowDetailsDto.toTvShowDetails(): TvShowDetails {
    return TvShowDetails(
        id = id,
        title = name,
        imageURL = "https://image.tmdb.org/t/p/w500/$posterPath",
        voteCount = voteCount ?: 0,
        voteAverage = voteAverage ?: 0.0,
        overview = overview ?: "",
        lastEpisode = lastEpisodeToAir?.episodeNumber ?: 0,
        lastSeason = lastEpisodeToAir?.seasonNumber ?: 0,
        network = networks?.get(0)?.name ?: "",
        genres = genres.map { it.id.toGenre() },
        date = firstAirDate ?: "",
        originCountry = originCountry?.get(0) ?: "",
        seasonCount = this.numberOfSeasons?:0,
        episodesCount = this.numberOfEpisodes?:0
    )
}