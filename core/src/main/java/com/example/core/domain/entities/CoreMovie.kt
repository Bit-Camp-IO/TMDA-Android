package com.example.core.domain.entities

abstract class CoreMovie (
    val adult: Boolean,
    val backdrop_path: String?,
    val genres: List<CoreGenre>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int


)
abstract class CoreGenre(val id: Int)
class x(x:Int):CoreGenre(x)