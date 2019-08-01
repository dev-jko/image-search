package com.nadarm.imagesearch.domain.useCase

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.ImageDocumentRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImageDocuments @Inject constructor(
    private val repository: ImageDocumentRepository
) : SingleUseCase4<String, Int, Int, Int, List<ImageDocument>> {

    override fun execute(query: String, page: Int, startIndex: Int, length: Int): Single<List<ImageDocument>> {
        return this.repository.getImageDocuments(query, page)
            .map { documentSubList(it, startIndex, length) }
    }

    private fun documentSubList(documents: List<ImageDocument>, startIndex: Int, length: Int): List<ImageDocument> {
        return when {
            startIndex < documents.size && startIndex + length <= documents.size -> documents.subList(
                startIndex,
                startIndex + length
            )
            startIndex < documents.size -> documents.subList(startIndex, documents.size)
            else -> emptyList()
        }
    }

}
