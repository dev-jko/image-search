package com.nadarm.imagesearcher.domain.useCase

import com.nadarm.imagesearcher.domain.model.QueryResponse
import com.nadarm.imagesearcher.domain.repository.QueryResponseRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetQueryResponse @Inject constructor(
    private val repository: QueryResponseRepository
) : SingleUseCase2<String, Int, QueryResponse> {

    override fun execute(query: String, page: Int): Single<QueryResponse> {
        return this.repository.getQueryResponse(query, page)
    }

}
