package com.example.tmda.di

import com.bitIO.tvshowcomponent.data.repository.TvShowRepositoryImpl
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import com.example.authentication.data.repositories.UserRepositoryImpl
import com.example.authentication.domain.repositories.UserRepository
import com.example.movies.data.repositories.MoviesRepositoryImpl
import com.example.movies.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractionModule {
//    @Binds
//    @Singleton
//    abstract fun bindMoviesApi(
//        moviesRepositoryImpl: TmdaApiServices
//    ): MoviesApiService

  /*  @Binds
    @Singleton
    abstract fun bindTvShowApi(
        moviesRepositoryImpl: TmdaApiServices
    ): TvShowApiService
*/
    @Binds
    @Singleton
    abstract fun bindMoviesRepo(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepo(
        authRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindTvShowRepo(
        tvShowRepositoryImpl: TvShowRepositoryImpl
    ): TvShowRepository

//  @Binds
//  @Singleton
//  abstract fun bindSessionProvider(
//    tvShowRepositoryImpl: TvShowRepositoryImpl
//  ): SessionProvider

}