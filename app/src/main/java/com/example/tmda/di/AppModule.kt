package com.example.tmda.di

import android.app.Application
import androidx.room.Room
import com.bitIO.tvshowcomponent.data.local.abstractions.TvShowDao
import com.example.authentication.data.local.UserDao
import com.example.authentication.data.remote.UserApiServices
import com.example.movies.data.local.MoviesDao
import com.example.movies.data.remote.MoviesApiService
import com.example.tmda.infrastructure.local.TmdaDatabase
import com.example.tmda.infrastructure.remote.TmdaRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(context: Application): TmdaDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            TmdaDatabase::class.java,
            "TMDA database"
        ).build()
    }

    //Movies
    @Provides
    @Singleton
    fun provideMoviesDao(database: TmdaDatabase): MoviesDao {
        return database.moviesDao()
    }

    @Provides
    @Singleton
    fun provideMoviesApiService(): MoviesApiService {
        return TmdaRemoteDataSource.tmdbService
    }

    //TvShow
    @Provides
    @Singleton
    fun provideTvShowDao(database: TmdaDatabase): TvShowDao {
        return database.tvShowDao()
    }


    //Auth

    @Provides
    @Singleton
    fun provideUserDao(database: TmdaDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideUserApiService(): UserApiServices {
        return TmdaRemoteDataSource.tmdbService
    }


//    @Provides
//    @Singleton
//    fun provideTvShowApiService(): TvShowApiService {
//        return TmdaRemoteDataSource.tmdaService
//    }

//    @Provides
//    @Singleton
//    fun provideMoviesRepo(
//        moviesApiService: MoviesApiService,
//        moviesDao: MoviesDao
//    ): MoviesRepository {
//        return MoviesRepositoryImpl(moviesApiService, moviesDao)
//    }
//

}