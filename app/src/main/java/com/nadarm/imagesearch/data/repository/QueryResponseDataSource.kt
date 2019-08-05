package com.nadarm.imagesearch.data.repository

import com.nadarm.imagesearch.domain.model.QueryResponse
import com.nadarm.imagesearch.domain.repository.QueryResponseRepository

interface QueryResponseDataSource : QueryResponseRepository {

    interface Remote : QueryResponseDataSource {

    }

    interface Cache : QueryResponseDataSource {
        fun isExistAndFresh(query: String, page: Int): Boolean
        fun pushQueryResponse(query: String, page: Int, queryResponse: QueryResponse)
    }

}