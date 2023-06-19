package com.example.authentication.data.mappers

import com.example.authentication.data.dto.RequestTokenDetailsDto
import com.example.authentication.data.dto.UserDetailsDto
import com.example.authentication.domain.entities.UserDetails
import okhttp3.MediaType
import okhttp3.RequestBody


fun RequestTokenDetailsDto.toRequestBody(): RequestBody {
    val mediaType = MediaType.parse("application/json")
    return RequestBody.create(mediaType, "{\"request_token\":\"$requestToken\"}")

}


fun String.toRequestBody(): RequestBody {
    val mediaType = MediaType.parse("application/json")
    return RequestBody.create(mediaType, "{\"session_id\":\"$this\"}")

}

fun UserDetailsDto.toUserDetails():UserDetails{
    return UserDetails(
        id,
        name,
        username,
        avatar.tmdb.avatarPath
    )
}