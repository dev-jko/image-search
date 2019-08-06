package com.nadarm.imagesearcher.data.repository

import com.nadarm.imagesearcher.domain.model.QueryResponse
import com.nadarm.imagesearcher.domain.repository.QueryResponseRepository

interface QueryResponseDataSource : QueryResponseRepository {

    interface Remote : QueryResponseDataSource {

    }

    interface Cache : QueryResponseDataSource {
        fun isExistAndFresh(query: String, page: Int): Boolean
        fun pushQueryResponse(query: String, page: Int, queryResponse: QueryResponse)
    }

}