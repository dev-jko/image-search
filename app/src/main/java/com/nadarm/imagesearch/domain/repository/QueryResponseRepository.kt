package com.nadarm.imagesearch.domain.repository

import com.nadarm.imagesearch.domain.model.QueryResponse
import io.reactivex.Single

interface QueryResponseRepository {

    fun getQueryResponse(query: String, page: Int = 1): Single<QueryResponse>

}