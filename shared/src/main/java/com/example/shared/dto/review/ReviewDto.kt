package com.example.shared.dto.review

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewDto(
    val author: String,
    val content: String,
    @Json(name = "updated_at") val updatedAt: String,
)