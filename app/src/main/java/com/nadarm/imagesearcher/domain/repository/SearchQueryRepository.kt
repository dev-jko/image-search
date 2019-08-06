package com.nadarm.imagesearcher.domain.repository

import android.database.Cursor
import io.reactivex.Completable

interface SearchQueryRepository {

    fun getSuggestions(query: String): Cursor

    fun addSearchedQuery(query: String): Completable

}