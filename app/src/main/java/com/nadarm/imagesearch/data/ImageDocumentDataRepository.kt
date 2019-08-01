package com.nadarm.imagesearch.data

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.ImageDocumentRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageDocumentDataRepository @Inject constructor(
    private val local: ImageDocumentDataSource.Local,
    private val remote: ImageDocumentDataSource.Remote
) : ImageDocumentRepository {

    override fun getImageDocuments(query: String, page: Int): Single<List<ImageDocument>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getImageDocument(query: String, page: Int, index: Int): Single<ImageDocument> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}