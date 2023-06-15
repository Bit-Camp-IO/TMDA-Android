package com.example.authentication.domain.repositories

import com.example.authentication.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createLoginSession(userName: String, password: String): User

    suspend fun deleteSession(): Boolean
    suspend fun getCurrentUser(): User
    fun getUserState(): Flow<User?>

    suspend fun addSeriesToWatchList(seriesId:Int,isAddRequest:Boolean): Unit
    suspend fun addMovieToWatchList(movieId:Int,isAddRequest:Boolean): Unit
    suspend fun getTvSavedState(seriesId: Int): Boolean
}