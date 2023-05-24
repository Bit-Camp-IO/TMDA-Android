package com.example.movies.domain.enities

import androidx.room.ColumnInfo


data class Genre(@ColumnInfo(name = "genre_code") val id: Int, val name: String)
