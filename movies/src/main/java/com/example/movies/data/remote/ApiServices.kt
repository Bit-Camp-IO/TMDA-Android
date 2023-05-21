package com.example.movies.data.remote

import com.example.movies.domain.enities.MovieDetails

interface ApiServices {
    suspend fun getMovieDetails(id: Int): MovieDetails
}