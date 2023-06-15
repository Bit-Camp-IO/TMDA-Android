package com.example.movies.domain.useCases

import com.example.movies.domain.enities.movie.MoviesPage
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject


class MovieUseCaseFactory @Inject constructor(
    private val repo: MoviesRepository
) {

    fun getUseCase(movieType: MovieType): MovieUseCase {
        return getMovieUseCase(movieType)
    }

    fun getUseCase(movieType: MovieTypeWithId, movieId: Int): MovieUseCaseWithId {
        return getMovieWithIdUseCase(movieType, movieId)
    }

    interface BaseUseCase {
        suspend fun invoke(pageNumber: Int): Result<MoviesPage>
    }


    sealed class MovieUseCase(protected val repo: MoviesRepository) : BaseUseCase {
        class Upcoming(repo: MoviesRepository) : MovieUseCase(repo) {
            override suspend fun invoke(pageNumber: Int): Result<MoviesPage> {
                return try {
                    Result.success(repo.getUpComingMovies(pageNumber))
                }catch (e:Throwable){
                    Result.failure(e)
                }

            }
        }

        class NowPlaying(repo: MoviesRepository) : MovieUseCase(repo) {
            override suspend fun invoke(pageNumber: Int): Result<MoviesPage> {
                return try {
                    Result.success(repo.getNowPlayingMovies(pageNumber))
                }catch (e:Throwable){
                    Result.failure(e)
                }
            }
        }

        class TopRated(repo: MoviesRepository) : MovieUseCase(repo) {
            override suspend fun invoke(pageNumber: Int):  Result<MoviesPage> {
                return try {
                    Result.success(repo.getTopRatedMovies(pageNumber))
                }catch (e:Throwable){
                    Result.failure(e)
                }

            }
        }

        class Popular(repo: MoviesRepository) : MovieUseCase(repo) {
            override suspend fun invoke(pageNumber: Int):  Result<MoviesPage> {
                return try {
                    Result.success(repo.getPopularMovies(pageNumber))
                }catch (e:Throwable){
                    Result.failure(e)
                }

            }
        }


    }

    enum class MovieType {
        Upcoming,
        NowPlaying,
        TopRated,
        Popular,
    }

    private fun getMovieUseCase(movieType: MovieType) = when (movieType) {
        MovieType.Upcoming -> MovieUseCase.Upcoming(repo)
        MovieType.NowPlaying -> MovieUseCase.NowPlaying(repo)
        MovieType.TopRated -> MovieUseCase.TopRated(repo)
        MovieType.Popular -> MovieUseCase.Popular(repo)
    }

    //
    enum class MovieTypeWithId {
        Recommended,
        Similar
    }

    sealed class MovieUseCaseWithId(
        protected val repo: MoviesRepository,
        protected val movieId: Int
    ) : BaseUseCase {
        class Similar(repo: MoviesRepository, movieId: Int) : MovieUseCaseWithId(repo, movieId) {
            override suspend fun invoke(pageNumber: Int):  Result<MoviesPage> {
                return try {
                    Result.success(repo.getSimilarMovies(movieId = movieId, pageNumber))
                }catch (e:Throwable){
                    Result.failure(e)
                }


            }
        }

        class Recommended(repo: MoviesRepository, movieId: Int) :
            MovieUseCaseWithId(repo, movieId) {
            override suspend fun invoke(pageNumber: Int):  Result<MoviesPage> {
                return try {
                    Result.success(repo.getRecommendMovies(movieId = movieId, pageNumber))
                }catch (e:Throwable){
                    Result.failure(e)
                }
            }
        }
    }

    private fun getMovieWithIdUseCase(movieType: MovieTypeWithId, movieId: Int) = when (movieType) {
        MovieTypeWithId.Recommended -> MovieUseCaseWithId.Recommended(repo, movieId)
        MovieTypeWithId.Similar -> MovieUseCaseWithId.Similar(repo, movieId)
    }


}