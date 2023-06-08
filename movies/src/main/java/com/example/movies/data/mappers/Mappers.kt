package com.example.movies.data.mappers

import com.example.movies.data.dto.credits.CastMemberDto
import com.example.movies.data.dto.credits.CreditsDto
import com.example.movies.data.dto.credits.CrewMemberDto
import com.example.movies.data.dto.movies.MovieBriefDto
import com.example.movies.data.dto.movies.MovieDetailsDto
import com.example.movies.data.dto.movies.MoviesBriefWrapperDto
import com.example.movies.data.dto.review.ReviewDto
import com.example.movies.data.dto.shared.GenreDto
import com.example.movies.data.dto.shared.MovieCollectionDetailsDto
import com.example.movies.data.dto.videos.VideoDto
import com.example.movies.data.util.genreMap
import com.example.movies.domain.enities.Genre
import com.example.movies.domain.enities.MovieCollectionDetails
import com.example.movies.domain.enities.Video
import com.example.movies.domain.enities.credits.CastMember
import com.example.movies.domain.enities.credits.Credits
import com.example.movies.domain.enities.credits.CrewMember
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.enities.movie.MovieDetails
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.movies.domain.enities.review.Review
import okhttp3.MediaType
import okhttp3.RequestBody

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        isAdult = isAdult,
        backdropPath = backdropPath,
        movieCollectionDetails = movieCollectionDetailsDto?.toMovieCollectionDetails(),
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        hasVideo = hasVideo,
        overview = overview,
        popularity = popularity,
        title = title,
        genres = genres.map { it.toGenre() },
        id = id,
        status = status,
        tagline = tagline,
        homepage = homepage,
        runtime = runtime,
        productionCountry = productionCountries.getOrNull(0)?.countryCode ?: "N/A"
    )
}

fun MovieCollectionDetailsDto.toMovieCollectionDetails(): MovieCollectionDetails {
    return MovieCollectionDetails(
        id = id,
        name = name,
        posterPath = posterPath,
        backdropPath = backdropPath
    )
}

fun GenreDto.toGenre() = Genre(id = id, name = name)


fun MovieBriefDto.toMovie(): Movie {
    return Movie(
        isAdult = isAdult,
        backdropPath = backdropPath,
        genres = genreIds.map { it.toGenre() },
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        hasVideo = hasVideo,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}

fun MoviesBriefWrapperDto.toMoviePage() = MoviesPage(
    page = page,
    results = results.map { it.toMovie() },
    totalPages = totalPages,
    totalResults = totalResults

)

internal fun Int.toGenre(): Genre {
    return Genre(this, genreMap[this]!!)
}

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

fun ReviewDto.toReview() = Review(
    author = author,
    content = content,
    updatedAt = updatedAt
)

fun VideoDto.toVideo() = Video(key = key, site = site,type=type)


fun makePostMovieToWatchListBody(movieId:Int, isSaveRequest:Boolean): RequestBody {
    val mediaType = MediaType.parse("application/json")
    return RequestBody.create(mediaType, "{\"media_type\":\"movie\",\"media_id\":\"$movieId\",\"watchlist\":$isSaveRequest}")
}