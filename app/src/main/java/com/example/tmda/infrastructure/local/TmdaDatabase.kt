package com.example.tmda.infrastructure.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bitIO.tvshowcomponent.data.local.abstractions.TvShowLocalDataSource
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.example.movies.data.local.MoviesLocalDataSource
import com.example.movies.domain.enities.Movie

@Database(entities = [Movie::class, TvShow::class], version = 1, exportSchema = false)
@TypeConverters(GenreTypeConverter::class)
abstract class TmdaDatabase : RoomDatabase(), MoviesLocalDataSource, TvShowLocalDataSource