package com.example.tmda.infrastructure.remote

import com.example.moviesComponent.data.remote.ApiKeyInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TmdaRemoteDataSource {
    private val moshi by lazy {
        Moshi.Builder().build()
    }
    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor.getInstance())
            .build()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    val tmdbService: TmdaApiServices by lazy { retrofit.create(TmdaApiServices::class.java) }
}