package com.nadarm.imagesearch.data.cache

import com.nadarm.imagesearch.data.ImageDocumentDataSource
import com.nadarm.imagesearch.domain.model.ImageDocument
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDocumentCacheDataSource @Inject constructor() : ImageDocumentDataSource.Cache {

    private val cached: MutableList<Data> = LinkedList()

    override fun pushImageDocuments(query: String, page: Int, documents: List<ImageDocument>) {
        if (this.cached.size >= 5) {
            this.cached.removeAt(0)
        }
        cached.add(Data(query, page, documents))
    }

    override fun getImageDocuments(query: String, page: Int): Single<List<ImageDocument>> {
        val documents = this.cached.first { it.query == query && it.page == page }.documents
        return Single.just(documents)
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
        val documents: List<ImageDocument>
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