package com.nadarm.imagesearch.data

import com.nadarm.imagesearch.domain.repository.ImageDocumentRepository

interface ImageDocumentDataSource : ImageDocumentRepository {

    interface Remote : ImageDocumentDataSource {

    }

    interface Local : ImageDocumentDataSource {

    }

}