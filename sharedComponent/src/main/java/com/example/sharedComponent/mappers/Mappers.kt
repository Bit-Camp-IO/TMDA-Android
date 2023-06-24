package com.example.sharedComponent.mappers

import com.example.sharedComponent.dto.credits.CastMemberDto
import com.example.sharedComponent.dto.credits.CreditsDto
import com.example.sharedComponent.dto.credits.CrewMemberDto
import com.example.sharedComponent.dto.details.GenreDto
import com.example.sharedComponent.dto.people.PeoplePageDto
import com.example.sharedComponent.dto.people.PersonDetailsDto
import com.example.sharedComponent.dto.people.PersonDto
import com.example.sharedComponent.dto.review.ReviewDto
import com.example.sharedComponent.dto.videos.VideoDto
import com.example.sharedComponent.entities.Genre
import com.example.sharedComponent.entities.Video
import com.example.sharedComponent.entities.credits.CastMember
import com.example.sharedComponent.entities.credits.Credits
import com.example.sharedComponent.entities.credits.CrewMember
import com.example.sharedComponent.entities.people.PeoplePage
import com.example.sharedComponent.entities.people.Person
import com.example.sharedComponent.entities.people.PersonDetails
import com.example.sharedComponent.entities.review.Review

fun GenreDto.toGenre() = Genre(id = id, name = name)
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

fun VideoDto.toVideo() = Video(key = key, site = site, type = type)
fun PersonDetailsDto.toPersonDetails() = PersonDetails(
    biography,
    birthday ?: "N/A",
    deathDay,
    id,
    name,
    placeOfBirth ?: "N/A",
    popularity,
    profilePath
)

fun PersonDto.toPerson() = Person(
    adult,
    id,
    knownForDepartment,
    name,
    popularity,
    profilePath
)
fun PeoplePageDto.toPeoplePage() = PeoplePage(
    page,
    results.map { it.toPerson() },
    totalPages,
    totalResults

)
fun ReviewDto.toReview() = Review(
    author = author,
    content = content,
    updatedAt = updatedAt
)