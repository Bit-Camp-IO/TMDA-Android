package com.example.user.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val userName: String,
    val password: String,
    val sessionId: String,
)