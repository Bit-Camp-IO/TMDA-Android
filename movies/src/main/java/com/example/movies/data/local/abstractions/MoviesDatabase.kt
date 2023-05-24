package com.example.movies.data.local.abstractions

interface MoviesDatabase {
     fun userDao(): MoviesDao
}