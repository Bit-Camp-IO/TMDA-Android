package com.example.movies.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.data.local.abstractions.MoviesDatabase
import com.example.movies.domain.enities.Movie

@Database(entities = [Movie::class], version = 1)
abstract class RoomMoviesDatabase : RoomDatabase(), MoviesDatabase
val database = Room.databaseBuilder(
    context = TODO("Not yet implemented"),
    RoomMoviesDatabase::class.java,"Movies database"
).build()