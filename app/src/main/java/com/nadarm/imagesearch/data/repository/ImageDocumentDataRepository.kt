package com.nadarm.imagesearch.data.repository

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.ImageDocumentRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageDocumentDataRepository @Inject constructor(
    private val cache: ImageDocumentDataSource.Cache,
    private val remote: ImageDocumentDataSource.Remote
) : ImageDocumentRepository {

    override fun getImageDocuments(query: String, page: Int): Single<List<ImageDocument>> {
        return if (this.cache.isExistAndFresh(query, page)) {
            this.cache.getImageDocuments(query, page)
        } else {
            this.remote.getImageDocuments(query, page)
                .retry(2)
                .doOnSuccess { this.cache.pushImageDocuments(query, page, it) }
        }
    }
}