package com.example.movies.data.local


interface MoviesLocalDataSource {
     fun moviesDao(): MoviesDao
}