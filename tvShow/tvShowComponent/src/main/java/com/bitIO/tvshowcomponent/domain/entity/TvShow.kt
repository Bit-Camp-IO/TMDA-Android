package com.bitIO.tvshowcomponent.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvShows")
data class TvShow(
   @PrimaryKey val id: Int = 0,
    val title: String = "",
    val imageURL: String = ""
)