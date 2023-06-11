package com.example.tmda.infrastructure.remote

import com.bitIO.tvshowcomponent.data.remote.TvShowApiService
import com.example.authentication.data.remote.UserApiServices
import com.example.movies.data.remote.MoviesApiService

interface TmdaApiServices : MoviesApiService, TvShowApiService, UserApiServices