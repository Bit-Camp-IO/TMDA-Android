package com.example.account.domain.entities

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
