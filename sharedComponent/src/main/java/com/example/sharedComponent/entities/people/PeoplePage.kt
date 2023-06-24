package com.example.sharedComponent.entities.people

data class PeoplePage (
    val page:Int,
    val results:List<Person>,
    val totalPages:Int,
    val totalResults: Int
)