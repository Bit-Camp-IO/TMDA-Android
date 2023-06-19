package com.example.shared.mappers

import com.example.shared.dto.credits.CastMemberDto
import com.example.shared.dto.credits.CreditsDto
import com.example.shared.dto.credits.CrewMemberDto
import com.example.shared.dto.details.GenreDto
import com.example.shared.dto.people.PeoplePageDto
import com.example.shared.dto.people.PersonDetailsDto
import com.example.shared.dto.people.PersonDto
import com.example.shared.dto.review.ReviewDto
import com.example.shared.dto.videos.VideoDto
import com.example.shared.entities.Genre
import com.example.shared.entities.Video
import com.example.shared.entities.credits.CastMember
import com.example.shared.entities.credits.Credits
import com.example.shared.entities.credits.CrewMember
import com.example.shared.entities.people.PeoplePage
import com.example.shared.entities.people.Person
import com.example.shared.entities.people.PersonDetails
import com.example.shared.entities.review.Review

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