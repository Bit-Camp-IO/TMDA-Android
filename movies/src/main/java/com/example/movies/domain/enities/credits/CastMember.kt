package com.example.movies.domain.enities.credits

data class CastMember(
    override val id: Int,
    override val role: String,
    override val name: String,
    override val profilePath: String?,
    val character: String,
) : CreditItem
