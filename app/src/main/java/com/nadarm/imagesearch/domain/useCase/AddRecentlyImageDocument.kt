package com.nadarm.imagesearch.domain.useCase

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.ImageDocumentRepository
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddRecentlyImageDocument @Inject constructor(
    private val repository: ImageDocumentRepository
) : CompletableUseCase1<ImageDocument> {

    override fun execute(imageDocument: ImageDocument): Completable {
        return repository.addRecentlyImageDocument(imageDocument)
    }

}