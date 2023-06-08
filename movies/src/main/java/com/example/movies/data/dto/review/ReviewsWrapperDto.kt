package com.example.movies.data.dto.review

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewsWrapperDto(
    val results: List<ReviewDto>
)