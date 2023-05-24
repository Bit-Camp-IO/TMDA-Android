package com.example.account.domain.repositories

import com.example.account.domain.entities.AccountDetails
import com.example.account.domain.entities.CollectionWrapper
import com.example.account.domain.entities.MoviesWrapper
import com.example.account.domain.entities.PostResponse
import com.example.account.domain.entities.RatedEpisodesWrapper
import com.example.account.domain.entities.TVWrapper

interface IAccountRepository {
    suspend fun getAccountDetails(accountId: Int): AccountDetails
    suspend fun getFavoriteMovies(accountId: Int): MoviesWrapper
    suspend fun getFavoriteTV(accountId: Int): TVWrapper
    suspend fun getCollections(accountId: Int): CollectionWrapper
    suspend fun getRatedMovies(accountId: Int): MoviesWrapper
    suspend fun getRatedTV(accountId: Int): TVWrapper
    suspend fun getRatedEpisodes(accountId: Int): RatedEpisodesWrapper
    suspend fun getWatchListMovies(accountId: Int): MoviesWrapper
    suspend fun getWatchListTV(accountId: Int): TVWrapper
    suspend fun addToFavorites(accountId: Int, movieId: Int): PostResponse
    suspend fun addToWatchList(accountId: Int, movieId: Int): PostResponse
}