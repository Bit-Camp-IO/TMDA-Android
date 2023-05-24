package com.example.authentication.data.repositories

import com.example.authentication.data.dto.RequestTokenDetailsDto
import com.example.authentication.data.mappers.toGuestSession
import com.example.authentication.data.mappers.toRequestBody
import com.example.authentication.data.remote.AuthApiServices
import com.example.authentication.domain.entities.GuestAuth
import com.example.authentication.domain.entities.UserAuth
import com.example.authentication.domain.repositories.AuthRepository
import okhttp3.MediaType
import okhttp3.RequestBody

class AuthRepositoryImpl(
    private val authApiServices: AuthApiServices,
) : AuthRepository {

    override suspend fun createGuestSession(): GuestAuth {
        return authApiServices.getGuestSession().toGuestSession()
    }

    override suspend fun createLoginSession(userName: String, password: String): UserAuth {
        val activeToken = getActiveToken(userName, password)
        val sessionId = authApiServices.createSession(activeToken.toRequestBody()).sessionId
        return UserAuth(userName, password, sessionId)
    }

    override suspend fun deleteSession(body: RequestBody): Boolean {
        return authApiServices.deleteSession(body)
    }

    //Helpers
    private suspend fun getActiveToken(
        userName: String,
        password: String
    ): RequestTokenDetailsDto {
        val requestToken = authApiServices.getRequestToken().requestToken
        val requestBody = makeLoginRequestBody(userName, password, requestToken)
        return authApiServices.createActiveTokenWithLogin(requestBody)
    }

    private fun makeLoginRequestBody(
        userName: String,
        password: String,
        requestToken: String
    ): RequestBody {
        val mediaType = MediaType.parse("application/json")
        val content =
            "{\"username\":\"$userName\",\"password\":\"$password\",\"request_token\":\"$requestToken\"}"
        return RequestBody.create(mediaType, content)


    }
}