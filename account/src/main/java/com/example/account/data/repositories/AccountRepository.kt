package com.example.account.data.repositories

import com.example.account.data.mappers.toAccountDetails
import com.example.account.data.mappers.toCollectionsWrapper
import com.example.account.data.mappers.toMoviesWrapper
import com.example.account.data.mappers.toPostResponse
import com.example.account.data.mappers.toRatedEpisodesWrapper
import com.example.account.data.mappers.toTVWrapper
import com.example.account.data.remote.IAccountRemoteDataSource
import com.example.account.domain.entities.AccountDetails
import com.example.account.domain.entities.CollectionWrapper
import com.example.account.domain.entities.MoviesWrapper
import com.example.account.domain.entities.PostResponse
import com.example.account.domain.entities.RatedEpisodesWrapper
import com.example.account.domain.entities.TVWrapper
import com.example.account.domain.repositories.IAccountRepository


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

    override suspend fun getRatedEpisodes(accountId: Int): RatedEpisodesWrapper {
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
