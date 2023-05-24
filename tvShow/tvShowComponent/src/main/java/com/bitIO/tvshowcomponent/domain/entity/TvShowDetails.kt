package com.bitIO.tvshowcomponent.domain.entity

data class TvShowDetails(
    val id: Int = 0,
    val title: String = "",
    val imageURL: String = "",
    val voteCount: Int = 0,
    val overview: String = ""
)