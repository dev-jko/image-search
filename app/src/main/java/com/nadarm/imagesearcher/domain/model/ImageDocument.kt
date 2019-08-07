package com.nadarm.imagesearcher.domain.model

data class ImageDocument(
    val query: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val docUrl: String,
    val displaySiteName: String,
    val page: Int,
    val index: Int
)