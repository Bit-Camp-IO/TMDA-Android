package com.example.authentication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.authentication.domain.entities.User

@Dao
interface UserDao {
    @Insert
    fun saveCurrentUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User

    @Query("DELETE FROM user")
    fun deleteCurrentUser()


}