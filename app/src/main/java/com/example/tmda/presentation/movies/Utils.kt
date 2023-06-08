package com.example.tmda.presentation.movies

fun getTmdbImageLink(path: String?) =
    if (path != null) "https://image.tmdb.org/t/p/w500$path" else null
