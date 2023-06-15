package com.bitIO.tvshowcomponent.domain.entity

data class TvShowPage(
    val page:Int,
    val results:List<TvShow>,
    val totalPages:Int,
    val totalResults:Int
)
