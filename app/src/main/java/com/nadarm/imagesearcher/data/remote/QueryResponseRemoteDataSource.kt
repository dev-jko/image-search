package com.nadarm.imagesearcher.data.remote

import com.nadarm.imagesearcher.data.model.mapper.QueryResponseMapper
import com.nadarm.imagesearcher.data.model.response.ImageSearchResponse
import com.nadarm.imagesearcher.data.remote.api.ApiRetrofit
import com.nadarm.imagesearcher.data.repository.QueryResponseDataSource
import com.nadarm.imagesearcher.domain.model.QueryResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QueryResponseRemoteDataSource @Inject constructor(
    private val retrofit: ApiRetrofit,
    private val mapper: QueryResponseMapper
) : QueryResponseDataSource.Remote {

    override fun getQueryResponse(
        query: String,
        page: Int
    ): Single<QueryResponse> {
        if (page < 1 || page > 50) {
            return Single.error(IndexOutOfBoundsException())
        }

        return retrofit.searchImage(query, page)
            .map { response: ImageSearchResponse -> mapper.mapFromData(query, response, page) }
    }
}