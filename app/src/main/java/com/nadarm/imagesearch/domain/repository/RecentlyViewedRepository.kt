package com.nadarm.imagesearch.domain.repository

import com.nadarm.imagesearch.domain.model.ImageDocument
import io.reactivex.Completable
import io.reactivex.Flowable

interface RecentlyViewedRepository {

    fun addRecentlyImageDocument(imageDocument: ImageDocument): Completable

    fun getRecentlyImageDocuments(): Flowable<List<ImageDocument>>

}