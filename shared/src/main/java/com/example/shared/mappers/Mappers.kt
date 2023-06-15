package com.example.shared.mappers

import com.example.shared.dto.credits.CastMemberDto
import com.example.shared.dto.credits.CreditsDto
import com.example.shared.dto.credits.CrewMemberDto
import com.example.shared.dto.videos.VideoDto
import com.example.shared.entities.Video
import com.example.shared.entities.credits.CastMember
import com.example.shared.entities.credits.Credits
import com.example.shared.entities.credits.CrewMember

fun CreditsDto.toCredits() = Credits(
    id = id,
    cast = cast.map { it.toCastMember() },
    crew = crew.map { it.toCrewMember() }
)
fun CastMemberDto.toCastMember() = CastMember(
    id = id,
    role = role,
    name = name,
    profilePath = profilePath,
    character = character
)

fun CrewMemberDto.toCrewMember() = CrewMember(
    id = id,
    role = role,
    name = name,
    profilePath = profilePath,
    job = job
)
fun VideoDto.toVideo() = Video(key = key, site = site,type=type)