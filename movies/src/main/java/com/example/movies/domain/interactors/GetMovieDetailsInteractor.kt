package com.example.movies.domain.interactors

import com.example.movies.domain.enities.movie.MovieDetails
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsInteractor @Inject constructor(private val repo: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): MovieDetails =
        repo.getMovieDetails(movieId)

}
