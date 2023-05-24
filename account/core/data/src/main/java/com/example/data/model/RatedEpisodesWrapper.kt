package com.example.data.model


data class RatedEpisodesWrapper(
    val page: Int,
    val results: List<Episode>,
    val totalPages: Int,
    val totalResults: Int
)