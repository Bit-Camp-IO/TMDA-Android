package com.example.sharedComponent.entities.credits

data class Credits(
    val id: Int,
    val cast: List<CastMember>,
    val crew: List<CrewMember>
)
