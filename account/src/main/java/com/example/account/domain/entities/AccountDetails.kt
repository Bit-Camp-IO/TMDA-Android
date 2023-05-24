package com.example.account.domain.entities

data class AccountDetails(
    val id: Int,
    val langISO: String,
    val countryISO: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String,
    val avatarPath: String?
)
