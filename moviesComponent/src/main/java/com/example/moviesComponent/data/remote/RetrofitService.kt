package com.example.moviesComponent.data.remote

//object RetrofitService {
//
//    private val moshi by lazy {
//        Moshi.Builder().build()
//    }
//    private val client by lazy {
//        OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor.getInstance())
//            .build()
//    }
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://api.themoviedb.org/3/").client(client)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }
//
//
//    val service: MoviesApiService by lazy { retrofit.create(MoviesApiService::class.java) }
//}