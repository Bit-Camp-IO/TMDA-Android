package com.example.user.data.remote

import com.example.user.data.dto.AccountSavedStateDto
import com.example.user.data.dto.DeleteDto
import com.example.user.data.dto.GuestSessionDto
import com.example.user.data.dto.RequestTokenDetailsDto
import com.example.user.data.dto.SessionDto
import com.example.user.data.dto.UserDetailsDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiServices {
    @GET("authentication/guest_session/new")
    suspend fun getGuestSession(): GuestSessionDto

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenDetailsDto

    @POST("authentication/session/new")
    suspend fun createSession(@Body body: RequestBody): SessionDto

    @POST("authentication/token/validate_with_login")
    suspend fun createActiveTokenWithLogin(@Body body: RequestBody): RequestTokenDetailsDto

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    //@DELETE("authentication/session")
    suspend fun deleteSession(@Body body: RequestBody): DeleteDto

    @POST("account/{account_id}/watchlist")
    suspend fun postToWatchList(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Body body: RequestBody
    )

    @GET("tv/{series_id}/account_states")
    suspend fun getTvSavedState(
        @Path("series_id") seriesId: Int,
        @Query("session_id") sessionId: String
    ): AccountSavedStateDto

    @GET("account/{account_id}")
    suspend fun getUserDetails(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String
    ): UserDetailsDto


}