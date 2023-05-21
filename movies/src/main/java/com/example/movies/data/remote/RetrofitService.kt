package com.example.movies.data.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitService {
    private val moshi by lazy {
        Moshi.Builder()
            .build()
    }
    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor.getInstance())
            .build()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/").client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    val service: ApiServices by lazy { retrofit.create(ApiServices::class.java) }
}