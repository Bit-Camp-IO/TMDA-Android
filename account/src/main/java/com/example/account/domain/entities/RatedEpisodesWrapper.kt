package com.example.account.domain.entities


data class RatedEpisodesWrapper(
    val page: Int,
    val results: List<Episode>,
    val totalPages: Int,
    val totalResults: Int
)