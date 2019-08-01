package com.nadarm.imagesearch.domain.model

data class ImageDocument(
    val thumbnailUrl: String,
    val imageUrl: String,
    val docUrl: String,
    val displaySiteName: String,
    val page: Int,
    val index: Int
)