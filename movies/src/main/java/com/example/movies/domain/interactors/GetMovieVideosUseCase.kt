package com.example.movies.domain.interactors

import com.example.movies.data.mappers.toVideo
import com.example.movies.domain.enities.Video
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend fun invoke(movieId: Int): List<Video> {
        return repo.getMovieVideos(movieId).results.map { it.toVideo() }
    }
}