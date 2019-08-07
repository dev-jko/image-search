package com.nadarm.imagesearcher.domain.model

data class SearchMeta(
    val query: String,
    val page: Int,
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean
)