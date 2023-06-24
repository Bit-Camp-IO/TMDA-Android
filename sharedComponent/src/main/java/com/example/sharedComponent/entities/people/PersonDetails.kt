package com.example.sharedComponent.entities.people

data class PersonDetails(
    val biography: String,
    val birthday: String,
    val deathDay: String?,
    val id: Int,
    val name: String,
    val placeOfBirth: String,
    val popularity: Double,
    val profilePath: String?
)
