package com.nadarm.imagesearcher.data.repository

import android.database.Cursor
import com.nadarm.imagesearcher.domain.repository.SearchQueryRepository
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchQueryDataRepository @Inject constructor(
    private val local: SearchQueryDataSource.Local
) : SearchQueryRepository {

    override fun getSuggestions(query: String): Cursor {
        return this.local.getSuggestions(query)
    }

    override fun addSearchedQuery(query: String): Completable {
        return this.local.addSearchedQuery(query)
    }
}
