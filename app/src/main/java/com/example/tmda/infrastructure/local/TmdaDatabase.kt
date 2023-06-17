package com.example.tmda.infrastructure.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bitIO.tvshowcomponent.data.local.TvShowLocalDataSource
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.authentication.data.local.UserLocalDataSource
import com.example.authentication.domain.entities.User
import com.example.movies.data.local.MoviesLocalDataSource
import com.example.movies.domain.enities.movie.Movie

@Database(entities = [Movie::class, TvShow::class, User::class], version = 1, exportSchema = false)
@TypeConverters(GenreTypeConverter::class)
abstract class TmdaDatabase : RoomDatabase(), MoviesLocalDataSource, TvShowLocalDataSource,UserLocalDataSource