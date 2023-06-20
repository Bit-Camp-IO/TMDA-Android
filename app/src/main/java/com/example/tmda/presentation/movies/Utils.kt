package com.example.tmda.presentation.movies

import com.example.shared.entities.Video

fun getTmdbImageLink(path: String?) =
    if (path != null) "https://image.tmdb.org/t/p/w500$path" else null

fun List<Video>.getParsedYoutubeList() =
    filter { it.site == "YouTube" && it.type == "Trailer" }
        .joinToString(separator = ",") { it.key }

