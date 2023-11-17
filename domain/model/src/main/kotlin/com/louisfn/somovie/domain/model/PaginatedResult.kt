package com.louisfn.somovie.domain.model

data class PaginatedResult<T>(
    val totalResults: Int,
    val totalPages: Int,
    val page: Int,
    val results: List<T>,
)
