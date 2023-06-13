package com.example.movies.domain.useCases

import com.example.movies.domain.enities.movie.MoviesPage
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    suspend fun invoke(keyword: String, page: Int): Result<MoviesPage> {
        return try {
            Result.success(repo.searchMovies(keyword = keyword, includeAdults = true, page = page))
        } catch (e: Throwable) {
            Result.failure(e)
        }

    }
}