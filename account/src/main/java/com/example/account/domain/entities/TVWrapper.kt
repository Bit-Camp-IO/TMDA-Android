package com.example.account.domain.entities


data class TVWrapper(
    val page: Int,
    val results: List<TV>,
    val totalPages: Int,
    val totalResults: Int
)