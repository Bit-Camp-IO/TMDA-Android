package com.example.movies.data.repositories

import com.example.movies.data.dto.account.MovieAccountStatesDto
import com.example.movies.data.dto.credits.CreditsDto
import com.example.movies.data.dto.image.ImageCollectionDto
import com.example.movies.data.dto.movies.LatestMovieDto
import com.example.movies.data.dto.videos.VideoContainerDto
import com.example.movies.data.local.MoviesDao
import com.example.movies.data.mappers.toMovieDetails
import com.example.movies.data.mappers.toMoviePage
import com.example.movies.data.remote.MoviesApiService
import com.example.movies.domain.enities.MovieDetails
import com.example.movies.domain.enities.MoviesPage
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService,
    private val moviesDao: MoviesDao
) : MoviesRepository {
    override suspend fun getMovieDetails(id: Int): MovieDetails {
        return moviesApiService.getMovieDetails(id).toMovieDetails()
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

    override suspend fun getRecommendMovies(id: Int, page: Int): MoviesPage {
        return moviesApiService.getRecommendMovies(id, page).toMoviePage()
    }

    override suspend fun getSimilarMovies(id: Int, page: Int): MoviesPage {
        return moviesApiService.getSimilarMovies(id, page).toMoviePage()
    }

    override suspend fun getNowPlayingMovies(page: Int): MoviesPage {
        return moviesApiService.getNowPlayingMovies(page).toMoviePage()
    }

    override suspend fun getPopularMovies(page: Int): MoviesPage {
        return moviesApiService.getPopularMovies(page).toMoviePage()
    }

    override suspend fun getTopRatedMovies(page: Int): MoviesPage {
        return moviesApiService.getTopRatedMovies(page).toMoviePage()
    }

    override suspend fun getUpComingMovies(page: Int): MoviesPage {
        return moviesApiService.getUpComingMovies(page).toMoviePage()
    }

    override suspend fun getTrendingMovies(): MoviesPage {
        return moviesApiService.getTrendingMovies().toMoviePage()
    }

    override suspend fun getMovieVideos(id: Int): VideoContainerDto {
        return moviesApiService.getMovieVideos(id)
    }


//    override fun getMoviesStream(movieId: Int): Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(20, enablePlaceholders = false),
//            pagingSourceFactory = {
//                MoviesPagingSourceWithId(
//                    apiServices = moviesApiService,
//                    movieId
//                )
//            }
//        ).flow
//    }
//
//    override fun getNowPlayingMoviesStream(): Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(20, enablePlaceholders = false),
//            pagingSourceFactory = { MoviesPagingSource(moviesApiService::getNowPlayingMovies) }
//        ).flow
//    }
//
//    override fun getPopularMoviesStream(): Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(20, enablePlaceholders = false),
//            pagingSourceFactory = { MoviesPagingSource(moviesApiService::getPopularMovies) }
//        ).flow
//    }
//
//    override fun getUpComingMoviesStream(): Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(20, enablePlaceholders = false),
//            pagingSourceFactory = { MoviesPagingSource(moviesApiService::getUpComingMovies) }
//        ).flow
//    }
//
//    override fun getTopRatedMoviesStream(): Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(20, enablePlaceholders = false),
//            pagingSourceFactory = { MoviesPagingSource(moviesApiService::getTopRatedMovies) }
//        ).flow
//    }


}