package com.example.shared.entities

data class Person(
    val biography: String,
    val birthday: String,
    val deathDay: String?,
    val id: Int,
    val name: String,
    val placeOfBirth: String,
    val popularity: Double,
    val profilePath: String?
)
