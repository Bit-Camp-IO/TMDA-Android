package com.example.user.data.mappers

import com.example.user.data.dto.RequestTokenDetailsDto
import com.example.user.data.dto.UserDetailsDto
import com.example.user.domain.entities.UserDetails
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
        avatar.tmdb.avatarPath?:""
    )
}