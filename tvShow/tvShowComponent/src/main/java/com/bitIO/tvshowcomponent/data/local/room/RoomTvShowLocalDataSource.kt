package com.bitIO.tvshowcomponent.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bitIO.tvshowcomponent.data.local.abstractions.TvShowLocalDataSource
import com.bitIO.tvshowcomponent.domain.entity.TvShow

@Database(entities = [TvShow::class], version = 1, exportSchema = false)
abstract class RoomTvShowLocalDataSource : RoomDatabase(), TvShowLocalDataSource
//
//class DataBaseHolder(private val context: Application) {
//    val database = Room.databaseBuilder(
//        context = context,
//        RoomTvShowDatabase::class.java, "Movies database"
//    ).build()
//}