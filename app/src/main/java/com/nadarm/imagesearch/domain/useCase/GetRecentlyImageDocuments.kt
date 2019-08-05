package com.nadarm.imagesearch.domain.useCase

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.RecentlyViewedRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRecentlyImageDocuments @Inject constructor(
    private val repository: RecentlyViewedRepository
) : SingleUseCase<List<ImageDocument>> {

    override fun execute(): Single<List<ImageDocument>> {
        return this.repository.getRecentlyImageDocuments()
    }

}