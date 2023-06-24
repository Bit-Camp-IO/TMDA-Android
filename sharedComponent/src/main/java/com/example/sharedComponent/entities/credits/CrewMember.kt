package com.example.sharedComponent.entities.credits

data class CrewMember(
    override val id: Int,
    override val role: String,
    override val name: String,
    override val profilePath: String?,
    val job: String
) : CreditItem
