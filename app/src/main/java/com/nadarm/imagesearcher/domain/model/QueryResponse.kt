package com.nadarm.imagesearcher.domain.model

data class QueryResponse(
    val meta: SearchMeta,
    val documents: List<ImageDocument>
)