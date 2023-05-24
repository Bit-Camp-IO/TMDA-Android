package com.example.movies.data.local.abstractions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movies.domain.enities.Movie

@Dao
interface MoviesDao {
    @Insert
    fun insertMovie(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<Movie>
}