package com.nadarm.imagesearch.data.repository

import com.nadarm.imagesearch.domain.model.QueryResponse
import com.nadarm.imagesearch.domain.repository.QueryResponseRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class QueryResponseDataRepository @Inject constructor(
    private val cache: QueryResponseDataSource.Cache,
    private val remote: QueryResponseDataSource.Remote
) : QueryResponseRepository {

    override fun getQueryResponse(query: String, page: Int): Single<QueryResponse> {
        return if (this.cache.isExistAndFresh(query, page)) {
            this.cache.getQueryResponse(query, page)
        } else {
            this.remote.getQueryResponse(query, page)
                .retry(2)
                .doOnSuccess { this.cache.pushQueryResponse(query, page, it) }
        }
    }
}