package com.example.authentication.data.remote

import com.example.authentication.data.dto.GuestSessionDto
import com.example.authentication.data.dto.RequestTokenDetailsDto
import com.example.authentication.data.dto.SessionDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiServices {
    @GET("authentication/guest_session/new")
    suspend fun getGuestSession(): GuestSessionDto

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenDetailsDto

    @POST("authentication/session/new")
    suspend fun createSession(@Body body: RequestBody): SessionDto

    @POST("authentication/token/validate_with_login")
    suspend fun createActiveTokenWithLogin(@Body body: RequestBody): RequestTokenDetailsDto

    @DELETE("authentication/session")
    suspend fun deleteSession(@Body body: RequestBody): Boolean


}