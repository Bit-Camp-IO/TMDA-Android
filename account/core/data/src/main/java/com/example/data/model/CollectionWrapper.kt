package com.example.data.model

data class CollectionWrapper(
    val page: Int,
    val results: List<Collection>,
    val totalPages: Int,
    val totalResults: Int
)