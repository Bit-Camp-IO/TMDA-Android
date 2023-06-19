package com.example.authentication.domain.entities

data class UserDetails(
    val id: Int,
    val name: String,
    val username: String,
    val userImage: String
)