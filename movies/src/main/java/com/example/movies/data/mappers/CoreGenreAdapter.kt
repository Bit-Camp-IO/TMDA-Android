package com.example.movies.data.mappers

import com.example.movies.domain.enities.core.Genre
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class CoreGenreAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): Genre {
        return if (reader.peek() == JsonReader.Token.NUMBER) {
            Genre(reader.nextInt(), null)
        } else {
            var id = 0
            var name: String? = null

            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "id" -> id = reader.nextInt()
                    "name" -> name = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            Genre(id, name)
        }

    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Genre) {
        if (value.name != null) {
            writer.beginObject()
            writer.name("id").value(value.id)
            writer.name("name").value(value.name)
            writer.endObject()
        } else {
            writer.value(value.id)
        }
    }
}