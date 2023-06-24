package com.example.user.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_state")
data class UserFirstLoginState(
    @PrimaryKey val isUSerFirstLogin: Boolean
)
