package com.example.network

import com.example.network.model.NetworkAccountDetails
import com.example.network.model.NetworkCollectionsWrapper
import com.example.network.model.NetworkMoviesWrapper
import com.example.network.model.NetworkPostResponse
import com.example.network.model.NetworkRatedEpisodesWrapper
import com.example.network.model.NetworkTVWrapper

interface IAccountRemoteDataSource {
    suspend fun getAccountDetails(accountId: Int): NetworkAccountDetails
    suspend fun getFavoriteMovies(accountId: Int): NetworkMoviesWrapper
    suspend fun getFavoriteTV(accountId: Int): NetworkTVWrapper
    suspend fun getLists(accountId: Int): NetworkCollectionsWrapper
    suspend fun getRatedMovies(accountId: Int): NetworkMoviesWrapper
    suspend fun getRatedTV(accountId: Int): NetworkTVWrapper
    suspend fun getRatedTVEpisodes(accountId: Int): NetworkRatedEpisodesWrapper
    suspend fun getWatchListMovies(accountId: Int): NetworkMoviesWrapper
    suspend fun getWatchListTV(accountId: Int): NetworkTVWrapper

    suspend fun addToFavorites(accountId: Int, movieId: Int): NetworkPostResponse
    suspend fun addToWatchList(accountId: Int, movieId: Int): NetworkPostResponse


}