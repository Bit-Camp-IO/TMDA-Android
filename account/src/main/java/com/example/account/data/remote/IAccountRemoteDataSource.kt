package com.example.account.data.remote

import com.example.account.data.dto.account.NetworkAccountDetails
import com.example.account.data.dto.collection.NetworkCollectionsWrapper
import com.example.account.data.dto.episode.NetworkRatedEpisodesWrapper
import com.example.account.data.dto.movie.NetworkMoviesWrapper
import com.example.account.data.dto.response.NetworkPostResponse
import com.example.account.data.dto.tv.NetworkTVWrapper

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