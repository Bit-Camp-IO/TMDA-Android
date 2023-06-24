package com.example.moviesComponent.data.local


interface MoviesLocalDataSource {
     fun moviesDao(): MoviesDao
}