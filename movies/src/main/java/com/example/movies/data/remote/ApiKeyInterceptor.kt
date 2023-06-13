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
        fun getInstance(
            apiKey: String = "45fe17b977fb94a0c0c099b894af2c74",
        ): ApiKeyInterceptor {
            return instance ?: synchronized(this) {
                instance ?: ApiKeyInterceptor(apiKey,

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
            .build()
        return chain.proceed(modifiedRequest)
    }
}
