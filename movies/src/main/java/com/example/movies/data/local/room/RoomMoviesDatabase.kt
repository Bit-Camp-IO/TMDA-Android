package com.example.movies.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies.data.local.abstractions.MoviesDatabase
import com.example.movies.domain.enities.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(GenreTypeConverter::class)
abstract class RoomMoviesDatabase : RoomDatabase(), MoviesDatabase

val database = Room.databaseBuilder(
    context = TODO("Not yet implemented"),
    RoomMoviesDatabase::class.java,"Movies database"
).build()
