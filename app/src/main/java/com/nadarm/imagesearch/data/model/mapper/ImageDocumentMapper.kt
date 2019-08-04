package com.nadarm.imagesearch.data.model.mapper

import com.nadarm.imagesearch.data.model.response.ImageSearchResponse
import com.nadarm.imagesearch.domain.model.ImageDocument

object ImageDocumentMapper {

    fun mapFromData(query: String, imageSearchResponse: ImageSearchResponse, page: Int): List<ImageDocument> {
        var index = 0
        return imageSearchResponse.documents.map {
            ImageDocument(
                query,
                it.thumbnailUrl,
                it.imageUrl,
                it.docUrl,
                it.displaySiteName,
                page,
                index++
            )
        }
    }

}