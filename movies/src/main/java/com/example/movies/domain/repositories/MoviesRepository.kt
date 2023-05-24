package com.example.movies.domain.repositories

import com.example.movies.data.dto.account.MovieAccountStatesDto
import com.example.movies.data.dto.credits.CreditsDto
import com.example.movies.data.dto.image.ImageCollectionDto
import com.example.movies.data.dto.movies.LatestMovieDto
import com.example.movies.data.dto.movies.MovieDetailsDto
import com.example.movies.data.dto.movies.MoviesBriefWrapperDto
import com.example.movies.data.dto.videos.VideoContainerDto

interface MoviesRepository {
    suspend fun getMovieDetails(id: Int): MovieDetailsDto
    suspend fun getMovieAccountStates(id: Int, sessionId: String): MovieAccountStatesDto
    suspend fun getMovieCredits(id: Int): CreditsDto
    suspend fun getMovieImages(id: Int, languageCode: String? = null): ImageCollectionDto
    suspend fun getLatestMovie(): LatestMovieDto
    suspend fun getRecommendMovies(id: Int, page: Int = 1): MoviesBriefWrapperDto
    suspend fun getSimilarMovies(id: Int, page: Int = 1): MoviesBriefWrapperDto
    suspend fun getMovieVideos(id: Int): VideoContainerDto
}