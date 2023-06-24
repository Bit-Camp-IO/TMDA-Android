package com.example.user.data.local

interface UserLocalDataSource {
    fun userDao(): UserDao
}