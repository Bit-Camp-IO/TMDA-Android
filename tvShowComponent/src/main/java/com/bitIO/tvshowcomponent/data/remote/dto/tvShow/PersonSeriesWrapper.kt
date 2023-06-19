package com.bitIO.tvshowcomponent.data.remote.dto.tvShow

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PersonSeriesWrapper(
    val cast: List<TvShowDto>,
    val crew: List<TvShowDto>
)





