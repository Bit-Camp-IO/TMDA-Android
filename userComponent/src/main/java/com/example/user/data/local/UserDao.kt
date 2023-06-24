package com.example.user.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.user.domain.entities.User
import com.example.user.domain.entities.UserFirstLoginState

@Dao
interface UserDao {
    @Insert
    fun saveCurrentUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User

    @Query("DELETE FROM user")
    fun deleteCurrentUser()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserFirstTime(userFirstLoginState: UserFirstLoginState)

    @Query("SELECT * FROM login_state LIMIT 1")
    fun getUserFirstLoginState(): UserFirstLoginState?


}