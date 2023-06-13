package com.example.tmda.di

import com.bitIO.tvshowcomponent.data.remote.TvShowApiService
import com.example.authentication.data.repositories.UserRepositoryImpl
import com.example.authentication.domain.repositories.UserRepository
import com.example.movies.data.repositories.MoviesRepositoryImpl
import com.example.movies.domain.repositories.MoviesRepository
import com.example.tmda.infrastructure.remote.TmdaApiServices
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

    @Binds
    @Singleton
    abstract fun bindTvShowApi(
        moviesRepositoryImpl: TmdaApiServices
    ): TvShowApiService

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


}