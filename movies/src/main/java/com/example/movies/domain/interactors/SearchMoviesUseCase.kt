package com.example.movies.domain.interactors

import com.example.movies.domain.enities.movie.MoviesPage
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    suspend fun invoke(keyword: String, page: Int): MoviesPage {
        return repo.searchMovies(keyword = keyword, includeAdults = true, page = page)
    }
}