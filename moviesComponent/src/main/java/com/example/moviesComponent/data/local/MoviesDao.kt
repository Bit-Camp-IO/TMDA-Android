package com.example.moviesComponent.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesComponent.domain.enities.movie.Movie

@Dao
interface MoviesDao {
    @Insert
    fun insertMovie(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<Movie>
}