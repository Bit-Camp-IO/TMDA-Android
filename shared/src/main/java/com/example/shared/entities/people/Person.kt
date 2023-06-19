package com.example.shared.entities.people

data class Person(
    val adult: Boolean,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val popularity: Double,
    val profilePath: String?
)


