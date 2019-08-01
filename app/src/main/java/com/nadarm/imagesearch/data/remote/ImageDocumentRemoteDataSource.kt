package com.nadarm.imagesearch.data.remote

import com.nadarm.imagesearch.data.repository.ImageDocumentDataSource
import com.nadarm.imagesearch.data.model.mapper.ImageDocumentMapper
import com.nadarm.imagesearch.data.model.response.ImageSearchResponse
import com.nadarm.imagesearch.data.remote.api.ApiRetrofit
import com.nadarm.imagesearch.domain.model.ImageDocument
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDocumentRemoteDataSource @Inject constructor(
    private val retrofit: ApiRetrofit,
    private val mapper: ImageDocumentMapper
) : ImageDocumentDataSource.Remote {

    override fun getImageDocuments(
        query: String,
        page: Int
    ): Single<List<ImageDocument>> {
        if (page < 1 || page > 50) {
            return Single.error(IndexOutOfBoundsException())
        }

        return retrofit.searchImage(query, page)
            .map { response: ImageSearchResponse -> mapper.mapFromData(response, page) }
    }
}