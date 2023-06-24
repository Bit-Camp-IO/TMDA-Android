package com.example.sharedComponent.entities.credits

data class CastMember(
    override val id: Int,
    override val role: String,
    override val name: String,
    override val profilePath: String?,
    val character: String,
) : CreditItem
