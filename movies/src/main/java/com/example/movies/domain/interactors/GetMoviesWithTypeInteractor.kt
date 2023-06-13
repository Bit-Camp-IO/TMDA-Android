package com.example.movies.domain.interactors

import com.example.movies.domain.enities.movie.MoviesPage
import com.example.movies.domain.repositories.MoviesRepository
import javax.inject.Inject


class GetMoviesWithTypeInteractor @Inject constructor(
    private val repo: MoviesRepository
) {

    fun invoke(movieType: MovieType): MovieUseCase {
        return getMovieUseCase(movieType)
    }

    fun invoke(movieTypeWithId: MovieTypeWithId, movieId: Int): MovieUseCaseWithId {
        return getMovieWithIdUseCase(movieTypeWithId, movieId)
    }

    interface BaseUseCase {
        suspend fun invoke(pageNumber: Int): MoviesPage
    }


    sealed class MovieUseCase(protected val repo: MoviesRepository) : BaseUseCase {
        class Upcoming(repo: MoviesRepository) : MovieUseCase(repo) {
            override suspend fun invoke(pageNumber: Int): MoviesPage {
                return repo.getUpComingMovies(pageNumber)
            }
        }

        class NowPlaying(repo: MoviesRepository) : MovieUseCase(repo) {
            override suspend fun invoke(pageNumber: Int): MoviesPage {
                return repo.getNowPlayingMovies(pageNumber)
            }
        }

        class TopRated(repo: MoviesRepository) : MovieUseCase(repo) {
            override suspend fun invoke(pageNumber: Int): MoviesPage {
                return repo.getTopRatedMovies(pageNumber)
            }
        }

        class Popular(repo: MoviesRepository) : MovieUseCase(repo) {
            override suspend fun invoke(pageNumber: Int): MoviesPage {
                return repo.getPopularMovies(pageNumber)
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
            override suspend fun invoke(pageNumber: Int): MoviesPage {
                return repo.getSimilarMovies(movieId = movieId, pageNumber)
            }
        }

        class Recommended(repo: MoviesRepository, movieId: Int) :
            MovieUseCaseWithId(repo, movieId) {
            override suspend fun invoke(pageNumber: Int): MoviesPage {
                return repo.getRecommendMovies(movieId = movieId, pageNumber)
            }
        }
    }

    private fun getMovieWithIdUseCase(movieType: MovieTypeWithId, movieId: Int) = when (movieType) {
        MovieTypeWithId.Recommended -> MovieUseCaseWithId.Recommended(repo, movieId)
        MovieTypeWithId.Similar -> MovieUseCaseWithId.Similar(repo, movieId)
    }


}