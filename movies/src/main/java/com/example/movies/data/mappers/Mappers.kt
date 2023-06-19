package com.example.movies.data.mappers


import com.example.movies.data.dto.movies.MovieBriefDto
import com.example.movies.data.dto.movies.MovieDetailsDto
import com.example.movies.data.dto.movies.MoviesBriefWrapperDto
import com.example.movies.data.dto.shared.MovieCollectionDetailsDto
import com.example.movies.data.dto.videos.VideoDto
import com.example.movies.data.util.genreMap
import com.example.movies.domain.enities.MovieCollectionDetails
import com.example.movies.domain.enities.Video
import com.example.movies.domain.enities.movie.Movie
import com.example.movies.domain.enities.movie.MovieDetails
import com.example.movies.domain.enities.movie.MoviesPage
import com.example.shared.dto.people.PeoplePageDto
import com.example.shared.dto.review.ReviewDto
import com.example.shared.entities.Genre
import com.example.shared.entities.people.PeoplePage
import com.example.shared.entities.review.Review
import com.example.shared.mappers.toGenre
import com.example.shared.mappers.toPersonDetails
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


fun ReviewDto.toReview() = Review(
    author = author,
    content = content,
    updatedAt = updatedAt
)

fun VideoDto.toVideo() = Video(key = key, site = site, type = type)


fun makePostMovieToWatchListBody(movieId: Int, isSaveRequest: Boolean): RequestBody {
    val mediaType = MediaType.parse("application/json")
    return RequestBody.create(
        mediaType,
        "{\"media_type\":\"movie\",\"media_id\":\"$movieId\",\"watchlist\":$isSaveRequest}"
    )
}

