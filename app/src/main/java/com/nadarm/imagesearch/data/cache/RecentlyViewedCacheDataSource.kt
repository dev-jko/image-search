package com.nadarm.imagesearch.data.cache

import com.nadarm.imagesearch.data.repository.RecentlyViewedDataSource
import com.nadarm.imagesearch.domain.model.ImageDocument
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentlyViewedCacheDataSource @Inject constructor() : RecentlyViewedDataSource.Cache {

    private val cached: LinkedList<ImageDocument> = LinkedList()

    override fun addRecentlyImageDocument(imageDocument: ImageDocument): Completable {
        if (this.cached.contains(imageDocument)) {
            return Completable.complete()
        }
        if (this.cached.size >= 10) {
            this.cached.removeLast()
        }
        this.cached.addFirst(imageDocument)
        return Completable.complete()
    }


    override fun getRecentlyImageDocuments(): Flowable<List<ImageDocument>> {
        return Flowable.just(this.cached)
    }

}
