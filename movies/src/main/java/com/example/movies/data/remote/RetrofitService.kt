package com.example.movies.data.remote

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitService {
    private val moshi by lazy {
        Moshi.Builder()
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // Create your Retrofit service interface
    val service: ApiServices by lazy { retrofit.create(ApiServices::class.java) }
}