package com.example.authentication.domain.repositories

import com.example.authentication.domain.entities.User
import com.example.authentication.domain.entities.UserDetails
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createLoginSession(userName: String, password: String): User

    suspend fun deleteSession(): Boolean
    suspend fun getCurrentUser(): User
    fun getUserState(): Flow<User?>

    suspend fun addSeriesToWatchList(seriesId:Int,isAddRequest:Boolean)
    suspend fun addMovieToWatchList(movieId:Int,isAddRequest:Boolean)
    suspend fun getTvSavedState(seriesId: Int): Boolean
    suspend fun getUserDetails():UserDetails
    suspend fun getIsFirstUserLogin():Boolean

}