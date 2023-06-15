package com.bitIO.tvshowcomponent.domain.entity

data class TvShowDetails(
    val id: Int = 0,
    val title: String = "",
    val imageURL: String = "",
    val voteCount: Int = 0,
    val overview: String = "",
    val lastEpisode: Int = 0,
    val lastSeason: Int = 0,
    val network: String ,
    val genres: List<TvShowGenre> ,
    val date: String ,
    val originCountry: String ,
    val voteAverage: Double ,
    val seasonCount:Int,
    val episodesCount:Int
)