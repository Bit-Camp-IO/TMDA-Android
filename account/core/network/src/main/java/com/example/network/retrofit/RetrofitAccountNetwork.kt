package com.example.network.retrofit

import com.example.authentication.data.remote.ApiKeyInterceptor
import com.example.network.BuildConfig
import com.example.network.IAccountRemoteDataSource
import com.example.network.model.NetworkAccountDetails
import com.example.network.model.NetworkCollectionsWrapper
import com.example.network.model.NetworkMoviesWrapper
import com.example.network.model.NetworkPostResponse
import com.example.network.model.NetworkRatedEpisodesWrapper
import com.example.network.model.NetworkTVWrapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitAccountNetwork : IAccountRemoteDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/account/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    ApiKeyInterceptor.getInstance(
                        BuildConfig.apiKey,
                        BuildConfig.accessToken
                    )
                ).build()
        )
        .build()
        .create(RetrofitAccountApi::class.java)

    override suspend fun getAccountDetails(accountId: Int): NetworkAccountDetails {
        return networkApi.getAccountDetails(accountId)
    }

    override suspend fun getFavoriteMovies(accountId: Int): NetworkMoviesWrapper {
        return networkApi.getFavoriteMovies(accountId)
    }

    override suspend fun getFavoriteTV(accountId: Int): NetworkTVWrapper {
        return networkApi.getFavoriteTV(accountId)
    }

    override suspend fun getLists(accountId: Int): NetworkCollectionsWrapper {
        return networkApi.getLists(accountId)
    }

    override suspend fun getRatedMovies(accountId: Int): NetworkMoviesWrapper {
        return networkApi.getRatedMovies(accountId)
    }

    override suspend fun getRatedTV(accountId: Int): NetworkTVWrapper {
        return networkApi.getRatedTV(accountId)
    }

    override suspend fun getRatedTVEpisodes(accountId: Int): NetworkRatedEpisodesWrapper {
        return networkApi.getRatedTVEpisodes(accountId)
    }

    override suspend fun getWatchListMovies(accountId: Int): NetworkMoviesWrapper {
        return networkApi.getWatchListMovies(accountId)
    }

    override suspend fun getWatchListTV(accountId: Int): NetworkTVWrapper {
        return networkApi.getWatchListTV(accountId)
    }

    override suspend fun addToFavorites(accountId: Int, movieId: Int): NetworkPostResponse {
        return networkApi.addToFavorites(accountId, movieId)
    }

    override suspend fun addToWatchList(accountId: Int, movieId: Int): NetworkPostResponse {
        return networkApi.addToWatchList(accountId, movieId)
    }
}