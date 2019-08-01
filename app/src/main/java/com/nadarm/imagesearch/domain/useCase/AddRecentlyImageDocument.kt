package com.nadarm.imagesearch.domain.useCase

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.RecentlyViewedRepository
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddRecentlyImageDocument @Inject constructor(
    private val repository: RecentlyViewedRepository
) : CompletableUseCase1<ImageDocument> {

    override fun execute(imageDocument: ImageDocument): Completable {
        return this.repository.addRecentlyImageDocument(imageDocument)
    }

}