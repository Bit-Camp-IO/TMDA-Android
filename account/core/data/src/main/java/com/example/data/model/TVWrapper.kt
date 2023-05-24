package com.example.data.model


data class TVWrapper(
    val page: Int,
    val results: List<TV>,
    val totalPages: Int,
    val totalResults: Int
)