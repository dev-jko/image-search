package com.nadarm.imagesearch.domain.useCase

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.ImageDocumentRepository
import com.nadarm.imagesearch.domain.repository.RecentlyViewedRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRecentlyImageDocuments @Inject constructor(
    private val repository: RecentlyViewedRepository
) : FlowableUseCase<List<ImageDocument>> {

    override fun execute(): Flowable<List<ImageDocument>> {
        return this.repository.getRecentlyImageDocuments()
    }

}