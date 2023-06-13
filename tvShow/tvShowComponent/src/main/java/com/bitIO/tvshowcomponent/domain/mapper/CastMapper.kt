package com.bitIO.tvshowcomponent.domain.mapper

import com.bitIO.tvshowcomponent.data.remote.response.CastDto
import com.bitIO.tvshowcomponent.domain.entity.Cast

fun CastDto.toDomain() =
    Cast(
        id = this.id ,
        name = this.name,
        character = this.character,
        photo = "https://image.tmdb.org/t/p/w500${this.profilePath}",
        order = this.order
    )