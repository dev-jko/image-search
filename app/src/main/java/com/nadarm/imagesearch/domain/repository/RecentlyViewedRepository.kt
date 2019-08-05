package com.nadarm.imagesearch.domain.repository

import com.nadarm.imagesearch.domain.model.ImageDocument
import io.reactivex.Completable
import io.reactivex.Single

interface RecentlyViewedRepository {

    fun addRecentlyImageDocument(imageDocument: ImageDocument): Completable

    fun getRecentlyImageDocuments(): Single<List<ImageDocument>>

}