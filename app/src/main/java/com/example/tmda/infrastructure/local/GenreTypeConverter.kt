package com.example.tmda.infrastructure.local

import androidx.room.TypeConverter
import com.bitIO.tvshowcomponent.domain.entity.TvShowGenre
import com.example.movies.domain.enities.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

//@ProvidedTypeConverter
class GenreTypeConverter {
    @TypeConverter
    fun stringToGenres(string: String) = try {
        Gson().fromJson<List<Genre>>(string)
    } catch (e: Exception) {
        listOf()
    }


    @TypeConverter
    fun genresToString(genres: List<Genre>) = Gson().toJson(genres)!!

    @TypeConverter
    fun tvGenresToString(genres: List<TvShowGenre?>?) = Gson().toJson(genres)!!
    @TypeConverter
    fun stringToTvGenres(string: String) = try {
        val moshi = Moshi.Builder().build()
        val genreList = Types.newParameterizedType(List::class.java, TvShowGenre::class.java)
        val adapter = moshi.adapter<List<TvShowGenre>>(genreList)
        adapter.fromJson(string)
    } catch (e: Exception) {
        listOf()
    }

}

 inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)!!