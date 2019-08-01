package com.nadarm.imagesearch.data

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.ImageDocumentRepository

interface ImageDocumentDataSource : ImageDocumentRepository {

    interface Remote : ImageDocumentDataSource {

    }

    interface Cache : ImageDocumentDataSource {
        fun isExistAndFresh(query: String, page: Int): Boolean
        fun pushImageDocuments(query: String, page: Int, documents: List<ImageDocument>)
    }

}