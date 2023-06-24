package com.example.sharedui.paging

data class UiPage<T : Any>(
    val page: Int,
    val results: List<T>,
    val totalPages: Int,
)