package com.example.tmda.di

import com.bitIO.tvshowcomponent.data.remote.TvShowInterceptor
import com.bitIO.tvshowcomponent.data.remote.TvShowService
import com.example.tmda.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideTvShowService(
        @RetrofitAppService retrofit: Retrofit,
    ): TvShowService {
        return retrofit.create(TvShowService::class.java)
    }

    @RetrofitAppService
    @Singleton
    @Provides
    fun provideRetrofitAppService(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(tvShowInterceptor: TvShowInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(tvShowInterceptor)
        }.build()


    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RetrofitAppService
}