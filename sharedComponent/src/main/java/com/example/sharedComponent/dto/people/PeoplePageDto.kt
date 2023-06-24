package com.example.sharedComponent.dto.people

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeoplePageDto(
    val page:Int,
    val results:List<PersonDto>,
    @Json(name = "total_pages")
    val totalPages:Int,
    @Json(name = "total_results")
    val totalResults: Int

)
