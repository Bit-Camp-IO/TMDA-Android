package com.bitIO.tvshowcomponent.domain.useCases.tvShow

import com.bitIO.tvshowcomponent.domain.entity.TvShowPage
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import javax.inject.Inject

class TvShowUseCaseFactory @Inject constructor(private val repository: TvShowRepository) {

    fun getUseCase(seriesType: SeriesType, movieId: Int = -1): TvShowUseCase {
        return when (seriesType) {
            SeriesType.OnTheAir -> TvShowUseCase.GetOnTheAirTvShowsUseCase(repository)
            SeriesType.NowPlaying -> TvShowUseCase.GetNowPlayingTvShowsUseCase(repository)
            SeriesType.TopRated -> TvShowUseCase.GetTopRatedTvShowsUseCase(repository)
            SeriesType.Popular -> TvShowUseCase.GetPopularTvShowsUseCase(repository)
            SeriesType.Similar -> TvShowUseCase.GetSimilarTvShowsUseCase(repository, movieId)
            SeriesType.Recommended -> TvShowUseCase.GetRecommendedTvShowsUseCase(repository, movieId)
        }
    }

    enum class SeriesType {
        OnTheAir,
        NowPlaying,
        TopRated,
        Popular,
        Similar,
        Recommended
    }

    sealed class TvShowUseCase(
        protected val repo: TvShowRepository,
        protected val tvShowId: Int = -1
    ) {

        internal class GetNowPlayingTvShowsUseCase(repo: TvShowRepository) : TvShowUseCase(repo) {
            override suspend operator fun invoke(pageIndex: Int): Result<TvShowPage> {
                return try {
                    Result.success(repo.getAiringTodayTvShows(pageIndex))
                } catch (e: Throwable) {
                    Result.failure(e)
                }
            }

        }

        internal class GetOnTheAirTvShowsUseCase(repo: TvShowRepository) : TvShowUseCase(repo) {
            override suspend operator fun invoke(pageIndex: Int): Result<TvShowPage> {
                return try {
                    Result.success(repo.getOnTheAirTvShows(pageIndex))
                } catch (e: Throwable) {

                    Result.failure(e)
                }

            }
        }

        internal class GetTopRatedTvShowsUseCase(repo: TvShowRepository) : TvShowUseCase(repo) {
            override suspend operator fun invoke(pageIndex: Int): Result<TvShowPage> {
                return try {
                    Result.success(repo.getTopRatedTvShows(pageIndex))
                } catch (e: Throwable) {
                    Result.failure(e)
                }
            }

        }

        internal class GetPopularTvShowsUseCase(repo: TvShowRepository) : TvShowUseCase(repo) {
            override suspend operator fun invoke(pageIndex: Int): Result<TvShowPage> {
                return try {
                    Result.success(repo.getPopularTvShows(pageIndex))
                } catch (e: Throwable) {
                    Result.failure(e)
                }
            }

        }

        internal class GetSimilarTvShowsUseCase(repo: TvShowRepository, tvShowId: Int) :
            TvShowUseCase(repo, tvShowId) {
            override suspend operator fun invoke(pageIndex: Int): Result<TvShowPage> {
                return try {
                    Result.success(repo.getSimilarTvShows(tvShowId, pageIndex))
                } catch (e: Throwable) {
                    Result.failure(e)
                }

            }

        }


        internal class GetRecommendedTvShowsUseCase(repo: TvShowRepository, tvShowId: Int) :
            TvShowUseCase(repo, tvShowId) {
            override suspend operator fun invoke(pageIndex: Int): Result<TvShowPage> {
                return try {
                    Result.success(repo.getRecommendedTvShows(tvShowId, pageIndex))
                } catch (e: Throwable) {
                    Result.failure(e)
                }
            }

        }

        abstract suspend operator fun invoke(pageIndex: Int): Result<TvShowPage>
    }
}