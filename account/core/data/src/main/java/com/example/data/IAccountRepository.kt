package com.example.data

import com.example.data.model.AccountDetails
import com.example.data.model.CollectionWrapper
import com.example.data.model.MoviesWrapper
import com.example.data.model.PostResponse
import com.example.data.model.RatedEpisodesWrapper
import com.example.data.model.TVWrapper

interface IAccountRepository {
    suspend fun getAccountDetails(accountId: Int): AccountDetails
    suspend fun getFavoriteMovies(accountId: Int): MoviesWrapper
    suspend fun getFavoriteTV(accountId: Int): TVWrapper
    suspend fun getCollections(accountId: Int): CollectionWrapper
    suspend fun getRatedMovies(accountId: Int): MoviesWrapper
    suspend fun getRatedTV(accountId: Int): TVWrapper
    suspend fun getRatedEpisode(accountId: Int): RatedEpisodesWrapper
    suspend fun getWatchListMovies(accountId: Int): MoviesWrapper
    suspend fun getWatchListTV(accountId: Int): TVWrapper
    suspend fun addToFavorites(accountId: Int, movieId: Int): PostResponse
    suspend fun addToWatchList(accountId: Int, movieId: Int): PostResponse
}