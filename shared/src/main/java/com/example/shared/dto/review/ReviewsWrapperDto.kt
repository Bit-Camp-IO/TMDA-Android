package com.example.shared.dto.review

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewsWrapperDto(
    val results: List<ReviewDto>
)