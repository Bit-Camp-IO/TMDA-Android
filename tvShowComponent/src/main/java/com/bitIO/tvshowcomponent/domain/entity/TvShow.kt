package com.bitIO.tvshowcomponent.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvShows")
data class TvShow(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val releaseDate: String,
    val genres: List<TvShowGenre>,
    val name: String,
    val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int
)