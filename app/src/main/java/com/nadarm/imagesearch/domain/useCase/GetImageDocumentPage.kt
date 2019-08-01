package com.nadarm.imagesearch.domain.useCase

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.ImageDocumentRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImageDocumentPage @Inject constructor(
    private val repository: ImageDocumentRepository
) : SingleUseCase2<String, Int, List<ImageDocument>> {

    override fun execute(query: String, page: Int): Single<List<ImageDocument>> {
        return this.repository.getImageDocuments(query, page)
    }

}