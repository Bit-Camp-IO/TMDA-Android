package com.example.movies.data.remote

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor private constructor(private val apiKey: String
,// private val token: String
) :
    Interceptor {
    companion object {
        private var instance: ApiKeyInterceptor? = null
        //  private const val myToken =
        //   "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiN2E1MTFmNDExZWNhYTAyNzljM2Q3OTYzMWRiYTMwOCIsInN1YiI6IjYzYjgxZWEzNzM1MjA1MDBhNzhlZjQ2NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.eBlvrroZUpK2ldRZy-QEbaTKqwsva6JjHwijF3pdmhw"

        fun getInstance(
            apiKey: String = "b7a511f411ecaa0279c3d79631dba308",
            //token: String = myToken
        ): ApiKeyInterceptor {
            return instance ?: synchronized(this) {
                instance ?: ApiKeyInterceptor(apiKey,
                    //token
                ).also { instance = it }
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val modifiedUrl: HttpUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .addHeader("accept", "application/json")
          //  .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(modifiedRequest)
    }
}
