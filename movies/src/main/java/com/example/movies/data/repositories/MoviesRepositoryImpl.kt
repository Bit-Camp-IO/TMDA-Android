package com.example.movies.data.repositories

import com.example.movies.data.dto.account.MovieAccountStatesDto
import com.example.movies.data.dto.credits.CreditsDto
import com.example.movies.data.dto.image.ImageCollectionDto
import com.example.movies.data.dto.movies.LatestMovieDto
import com.example.movies.data.dto.movies.MovieDetailsDto
import com.example.movies.data.dto.movies.MoviesBriefWrapperDto
import com.example.movies.data.dto.videos.VideoContainerDto
import com.example.movies.data.local.abstractions.MoviesDao
import com.example.movies.data.remote.MoviesApiServices
import com.example.movies.domain.repositories.MoviesRepository

class MoviesRepositoryImpl(
    private val moviesApiServices: MoviesApiServices,
    private val moviesDao: MoviesDao
) : MoviesRepository {
    override suspend fun getMovieDetails(id: Int): MovieDetailsDto {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieAccountStates(id: Int, sessionId: String): MovieAccountStatesDto {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieCredits(id: Int): CreditsDto {
        TODO("Not yet implemented")

    }

    override suspend fun getMovieImages(id: Int, languageCode: String?): ImageCollectionDto {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestMovie(): LatestMovieDto {
        TODO("Not yet implemented")
    }

    override suspend fun getRecommendMovies(id: Int, page: Int): MoviesBriefWrapperDto {
        TODO("Not yet implemented")
    }

    override suspend fun getSimilarMovies(id: Int, page: Int): MoviesBriefWrapperDto {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieVideos(id: Int): VideoContainerDto {
        TODO("Not yet implemented")
    }
}