package com.example.moviesComponent.domain.useCases

import com.example.moviesComponent.domain.enities.movie.MoviesPage
import com.example.moviesComponent.domain.repositories.MoviesRepository
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