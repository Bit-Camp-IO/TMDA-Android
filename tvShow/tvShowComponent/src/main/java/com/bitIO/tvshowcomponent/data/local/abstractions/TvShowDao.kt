package com.bitIO.tvshowcomponent.data.local.abstractions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bitIO.tvshowcomponent.domain.entity.TvShow


@Dao
interface TvShowDao {
    @Insert
    fun insertTvShow(tvShow: TvShow)

    @Query("SELECT * FROM tvShows")
    fun getAllTvShows(): List<TvShow>

}