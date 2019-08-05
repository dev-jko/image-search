package com.nadarm.imagesearch.domain.model

data class QueryResponse(
    val meta: SearchMeta,
    val documents: List<ImageDocument>
)