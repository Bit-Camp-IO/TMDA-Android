package com.example.tmda.infrastructure.remote

import com.bitIO.tvshowcomponent.data.remote.TvShowApiService
import com.example.user.data.remote.UserApiServices
import com.example.moviesComponent.data.remote.MoviesApiService

interface TmdaApiServices : MoviesApiService, TvShowApiService, UserApiServices