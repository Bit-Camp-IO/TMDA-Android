package com.example.data

import com.example.data.model.AccountDetails
import com.example.data.model.CollectionWrapper
import com.example.data.model.Episode
import com.example.data.model.Movie
import com.example.data.model.MoviesWrapper
import com.example.data.model.PostResponse
import com.example.data.model.RatedEpisodesWrapper
import com.example.data.model.TV
import com.example.data.model.TVWrapper
import com.example.data.model.Collection
import com.example.network.IAccountRemoteDataSource
import com.example.network.model.NetworkAccountDetails
import com.example.network.model.NetworkCollection
import com.example.network.model.NetworkCollectionsWrapper
import com.example.network.model.NetworkEpisode
import com.example.network.model.NetworkMovie
import com.example.network.model.NetworkMoviesWrapper
import com.example.network.model.NetworkPostResponse
import com.example.network.model.NetworkRatedEpisodesWrapper
import com.example.network.model.NetworkTV
import com.example.network.model.NetworkTVWrapper

class AccountRepository(private val accountRemoteDataSource: IAccountRemoteDataSource) : IAccountRepository {
    override suspend fun getAccountDetails(accountId: Int): AccountDetails {
        return accountRemoteDataSource.getAccountDetails(accountId).toAccountDetails()
    }

    override suspend fun getFavoriteMovies(accountId: Int): MoviesWrapper {
        return accountRemoteDataSource.getFavoriteMovies(accountId).toMoviesWrapper()
    }

    override suspend fun getFavoriteTV(accountId: Int): TVWrapper {
        return accountRemoteDataSource.getFavoriteTV(accountId).toTVWrapper()
    }

    override suspend fun getCollections(accountId: Int): CollectionWrapper {
        return accountRemoteDataSource.getLists(accountId).toCollectionsWrapper()
    }

    override suspend fun getRatedMovies(accountId: Int): MoviesWrapper {
        return accountRemoteDataSource.getRatedMovies(accountId).toMoviesWrapper()
    }

    override suspend fun getRatedTV(accountId: Int): TVWrapper {
        return accountRemoteDataSource.getRatedTV(accountId).toTVWrapper()
    }

    override suspend fun getRatedEpisode(accountId: Int): RatedEpisodesWrapper {
        return accountRemoteDataSource.getRatedTVEpisodes(accountId).toRatedEpisodesWrapper()
    }

    override suspend fun getWatchListMovies(accountId: Int): MoviesWrapper {
        return accountRemoteDataSource.getWatchListMovies(accountId).toMoviesWrapper()
    }

    override suspend fun getWatchListTV(accountId: Int): TVWrapper {
        return accountRemoteDataSource.getWatchListTV(accountId).toTVWrapper()
    }

    override suspend fun addToFavorites(accountId: Int, movieId: Int): PostResponse {
        return accountRemoteDataSource.addToFavorites(accountId, movieId).toPostResponse()
    }

    override suspend fun addToWatchList(accountId: Int, movieId: Int): PostResponse {
        return accountRemoteDataSource.addToWatchList(accountId, movieId).toPostResponse()
    }
}

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