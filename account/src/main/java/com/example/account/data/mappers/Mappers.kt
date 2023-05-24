package com.example.account.data.mappers

import com.example.account.data.dto.account.NetworkAccountDetails
import com.example.account.data.dto.collection.NetworkCollection
import com.example.account.data.dto.collection.NetworkCollectionsWrapper
import com.example.account.data.dto.episode.NetworkEpisode
import com.example.account.data.dto.episode.NetworkRatedEpisodesWrapper
import com.example.account.data.dto.movie.NetworkMovie
import com.example.account.data.dto.movie.NetworkMoviesWrapper
import com.example.account.data.dto.response.NetworkPostResponse
import com.example.account.data.dto.tv.NetworkTV
import com.example.account.data.dto.tv.NetworkTVWrapper
import com.example.account.domain.entities.AccountDetails
import com.example.account.domain.entities.Collection
import com.example.account.domain.entities.CollectionWrapper
import com.example.account.domain.entities.Episode
import com.example.account.domain.entities.Movie
import com.example.account.domain.entities.MoviesWrapper
import com.example.account.domain.entities.PostResponse
import com.example.account.domain.entities.RatedEpisodesWrapper
import com.example.account.domain.entities.TV
import com.example.account.domain.entities.TVWrapper


fun NetworkAccountDetails.toAccountDetails() =
    AccountDetails(
        id = this.id,
        langISO = this.iso6391,
        countryISO = this.iso31661,
        name = this.name,
        includeAdult = this.includeAdult,
        username = this.username,
        avatarPath = this.avatar.tmdb.avatarPath
    )

fun NetworkMoviesWrapper.toMoviesWrapper() =
    MoviesWrapper(page, results.map { it.toMovie() }, totalPages, totalResults)

fun NetworkMovie.toMovie() = Movie(
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount,
    rating
)

fun NetworkTVWrapper.toTVWrapper() = TVWrapper(
    page = this.page,
    results = this.results.map { it.toTV() },
    totalPages = this.totalPages,
    totalResults = this.totalResults
)

fun NetworkTV.toTV() = TV(
    adult,
    backdropPath,
    genreIds,
    id,
    originCountry,
    originalLanguage,
    originalName,
    overview,
    popularity,
    posterPath,
    firstAirDate,
    name,
    voteAverage,
    voteCount,
    rating
)

fun NetworkCollection.toCollection() = Collection(description, favoriteCount, id, itemCount, iso6391, listType, name, posterPath)
fun NetworkCollectionsWrapper.toCollectionsWrapper() = CollectionWrapper(page, results.map { it.toCollection() }, totalPages, totalResults)

fun NetworkRatedEpisodesWrapper.toRatedEpisodesWrapper() =
    RatedEpisodesWrapper(page, results.map { it.toEpisode() }, totalPages, totalResults)

fun NetworkEpisode.toEpisode() =
    Episode(airDate, episodeNumber, id, name, overview, productionCode, runtime, seasonNumber, showId, stillPath, voteAverage, voteCount, rating)

fun NetworkPostResponse.toPostResponse() = PostResponse(success, statusMessage)