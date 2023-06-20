package com.example.movies.domain.useCases


import com.example.movies.domain.repositories.MoviesRepository
import com.example.shared.entities.Video
import com.example.shared.mappers.toVideo
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