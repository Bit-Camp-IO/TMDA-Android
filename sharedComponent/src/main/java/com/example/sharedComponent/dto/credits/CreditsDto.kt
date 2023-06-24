package com.example.sharedComponent.dto.credits

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditsDto(
    val id: Int,
    val cast: List<CastMemberDto>,
    val crew: List<CrewMemberDto>
)

