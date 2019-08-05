package com.nadarm.imagesearch.data.repository

import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.repository.RecentlyViewedRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentlyViewedDataRepository @Inject constructor(
    private val cache: RecentlyViewedDataSource.Cache
) : RecentlyViewedRepository {

    override fun addRecentlyImageDocument(imageDocument: ImageDocument): Completable {
        return this.cache.addRecentlyImageDocument(imageDocument)
    }

    override fun getRecentlyImageDocuments(): Single<List<ImageDocument>> {
        return this.cache.getRecentlyImageDocuments()
    }
}