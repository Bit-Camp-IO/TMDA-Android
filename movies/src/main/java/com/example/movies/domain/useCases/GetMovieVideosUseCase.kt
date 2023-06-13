package com.example.movies.domain.useCases

import com.example.movies.data.mappers.toVideo
import com.example.movies.domain.enities.Video
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend fun invoke(movieId: Int): Result<List<Video>> {
        return try {
            Result.success(repo.getMovieVideos(movieId).results.map { it.toVideo() })
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}