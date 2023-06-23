package com.example.authentication.data.local

interface UserLocalDataSource {
    fun userDao(): UserDao
}