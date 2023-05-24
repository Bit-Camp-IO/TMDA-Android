package com.example.data.model

data class Collection(
    val description: String,
    val favoriteCount: Int,
    val id: Int,
    val itemCount: Int,
    val langCode: String,
    val listType: String,
    val name: String,
    val posterPath: String?
)
