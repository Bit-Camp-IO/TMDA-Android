package com.example.tmda.di


import com.bitIO.tvshowcomponent.data.repository.TvShowRepositoryImp
import com.bitIO.tvshowcomponent.domain.repository.TvShowRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindTvShowRepository(
        tvShowRepository: TvShowRepositoryImp
    ): TvShowRepository
}