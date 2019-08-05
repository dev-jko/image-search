package com.nadarm.imagesearch.data.cache

import com.nadarm.imagesearch.data.repository.QueryResponseDataSource
import com.nadarm.imagesearch.domain.model.QueryResponse
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QueryResponseCacheDataSource @Inject constructor() : QueryResponseDataSource.Cache {

    private val cached: LinkedList<Data> = LinkedList()

    override fun pushQueryResponse(query: String, page: Int, queryResponse: QueryResponse) {
        if (this.cached.size >= 5) {
            this.cached.removeFirst()
        }
        cached.addLast(Data(query, page, queryResponse))
    }

    override fun getQueryResponse(query: String, page: Int): Single<QueryResponse> {
        val queryResponse = this.cached.first { it.query == query && it.page == page }.queryResponse
        return Single.just(queryResponse)
    }

    override fun isExistAndFresh(query: String, page: Int): Boolean {
        val index = isExist(query, page)
        if (index == -1) {
            return false
        }
        return isFresh(index)
    }

    private fun isExist(query: String, page: Int): Int {
        return cached.indexOfFirst { it.query == query && it.page == page }
    }

    private fun isFresh(index: Int): Boolean {
        if (cached[index].isFresh()) {
            return true
        }
        this.cached.removeAt(index)
        return false
    }


    private inner class Data(
        val query: String,
        val page: Int,
        val queryResponse: QueryResponse
    ) {
        val createdAt: Long = System.currentTimeMillis()

        fun isFresh(): Boolean {
            val current = System.currentTimeMillis()
            return (current - this.createdAt) < timeout
        }
    }


    companion object {
        private const val timeout: Long = 10 * 60 * 1000 // 10분
    }

}