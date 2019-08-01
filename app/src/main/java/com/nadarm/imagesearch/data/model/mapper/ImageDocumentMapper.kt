package com.nadarm.imagesearch.data.model.mapper

import com.nadarm.imagesearch.data.model.response.ImageSearchResponse
import com.nadarm.imagesearch.domain.model.ImageDocument

object ImageDocumentMapper {

    fun mapFromData(imageSearchResponse: ImageSearchResponse, page: Int): List<ImageDocument> {
        var index = 1
        return imageSearchResponse.documents.map {
            ImageDocument(
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