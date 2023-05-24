package com.example.authentication.data.mappers

import com.example.authentication.data.dto.GuestSessionDto
import com.example.authentication.data.dto.RequestTokenDetailsDto
import com.example.authentication.domain.entities.GuestAuth
import okhttp3.MediaType
import okhttp3.RequestBody

fun GuestSessionDto.toGuestSession() = GuestAuth(
    sessionId = sessionId,
    expirationDate = expirationDate,
)

fun RequestTokenDetailsDto.toRequestBody(): RequestBody {
    val mediaType = MediaType.parse("application/json")

    return RequestBody.create(mediaType, "{\"request_token\":\"$requestToken\"}")


}


