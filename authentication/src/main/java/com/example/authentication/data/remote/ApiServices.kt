package com.example.authentication.data.remote

import com.example.authentication.data.dto.GuestSessionDto
import com.example.authentication.data.dto.RequestTokenDto
import com.example.authentication.data.dto.SessionDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(): GuestSessionDto

    @GET("authentication/token/new")
    suspend fun createRequestToken(): RequestTokenDto

    @POST("authentication/session/new")
    suspend fun createSession(@Body body: RequestBody): SessionDto

    @POST("authentication/token/validate_with_login")
    suspend fun createLoginSession(@Body body: RequestBody): RequestTokenDto

    @DELETE("authentication/session")
    suspend fun deleteSession(@Body body: RequestBody): Boolean


}