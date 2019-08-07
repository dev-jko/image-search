package com.nadarm.imagesearcher.domain.repository

import com.nadarm.imagesearcher.domain.model.QueryResponse
import io.reactivex.Single

interface QueryResponseRepository {

    fun getQueryResponse(query: String, page: Int = 1): Single<QueryResponse>

}