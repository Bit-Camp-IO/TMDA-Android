package com.bitIO.tvshowcomponent.data.remote.response


import kotlinx.serialization.SerialName

data class BaseTvShowResponse(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<TvShowDto>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)