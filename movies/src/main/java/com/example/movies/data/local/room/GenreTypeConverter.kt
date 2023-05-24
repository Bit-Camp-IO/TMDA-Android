package com.example.movies.data.local.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.movies.domain.enities.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class GenreTypeConverter {
    @TypeConverter
    fun stringToGenres(string: String) = try {
        Gson().fromJson<List<Genre>>(string)
    } catch (e: Exception) { listOf() }


    @TypeConverter
    fun genresToString(genres: List<Genre>) = Gson().toJson(genres)!!

}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)!!